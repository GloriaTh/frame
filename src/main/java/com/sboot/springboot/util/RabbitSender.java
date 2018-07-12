package com.sboot.springboot.util;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class RabbitSender {
    @Autowired
    AmqpTemplate template;


    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.template.convertAndSend("hello", context);
    }
}
