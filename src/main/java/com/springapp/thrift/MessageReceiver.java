package com.springapp.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

/**
 * Created by kay on 15-11-18.
 */
public class MessageReceiver extends ConnectionRequiredRunnable {
    private final MessageService.Processor processor;
    private final TProtocol protocol;

    public MessageReceiver(
            TProtocol protocol,
            MessageService.Iface messageService,
            ConnectionStatusMonitor connectionMonitor) {
        super(connectionMonitor, "Message Receiver");
        this.protocol = protocol;
        this.processor = new MessageService.Processor(messageService);
    }

    @Override
    public void run() {
        connectWait();
        while (true) {
            try {
                while (processor.process(protocol, protocol) == true) {
                }
            } catch (TException e) {
                disconnected();
            }
        }
    }
}
