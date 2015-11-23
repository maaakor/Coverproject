package com.springapp.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by kay on 15-11-18.
 */
@Component
public class JavaClient {
    private ConnectionStatusMonitor connectionMonitor;
    private MessageSender sender;
    private MessageReceiver receiver;

    private String name;

    private TTransport transport;
    private TProtocol protocol;

    private List<MessageListener> listeners;

    public void start(String name, String server, int port, MessageService.Iface messageHandler) {
        this.name = name;
        this.transport = new TSocket(server, port);
        this.protocol = new TBinaryProtocol(transport);

        this.connectionMonitor = new ConnectionStatusMonitor(transport);

        this.sender = new MessageSender(protocol, connectionMonitor);
        this.receiver = new MessageReceiver(protocol, messageHandler, connectionMonitor);

        new Thread(sender).start();
        new Thread(receiver).start();

        this.connectionMonitor.tryOpen();

        this.listeners = new ArrayList<MessageListener>();
    }

    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }

    public void sendMessageToServer(String msg) {
        sender.send(new Message(name, msg));
    }


    public void sendMessage(Message msg) throws TException {
        for (MessageListener listener : listeners) {
            listener.messageReceived(msg);
        }
    }

    public JavaClient() {
        System.out.println("init client!");
        MessageService.Iface handler = new MessageService.Iface() {
            @Override
            public void sendMessage(Message msg) throws TException {
                if (msg != null) {
                    messageQueue.put(msg.getClientName(), msg);
                }
                System.out.println("Got msg: " + msg);
            }
        };

        start("kay", "localhost", 12345, handler);
        sendMessageToServer("Hello there!");
    }

    private Map<String, Object> messageQueue = new HashMap<String, Object>();


    public void init() {
        MessageService.Iface handler = new MessageService.Iface() {
            @Override
            public void sendMessage(Message msg) throws TException {
                messageQueue.put(msg.getClientName(), msg);
                System.out.println("Got msg: " + msg);
            }
        };

        // JavaClient client = new JavaClient("kay", "localhost", 12345, handler);

        sendMessageToServer("Hello there!");
    }
}
