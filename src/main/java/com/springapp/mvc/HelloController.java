package com.springapp.mvc;

import com.springapp.model.Greeting;
import com.springapp.model.HelloMessage;
import com.springapp.service.UserService;
import com.springapp.thrift.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Controller
@RequestMapping("/")
public class HelloController {
    public JavaClient getJavaClient() {
        return javaClient;
    }

    @Resource
    public void setJavaClient(JavaClient javaClient) {
        this.javaClient = javaClient;
    }


    public UserService getUserService() {
        return userService;
    }

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    JavaClient javaClient;


    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        //javaClient.start();

        userService.add();
        model.addAttribute("message", "Hello world!");
        return "echo";
    }

    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public String printToWeb(ModelMap model) {
        model.addAttribute("message", "Hello world print!");
        return "hello";
    }

  /*  @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(3000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }*/


}