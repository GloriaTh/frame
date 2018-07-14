package com.sboot.springboot.util;

import com.sboot.springboot.domin.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class RabbitSender {
    @Autowired
    AmqpTemplate template;


//    public void send() {
//        String context = "hello " + new Date();
//        System.out.println("Sender : " + context);
//        this.template.convertAndSend("hello", context);
//    }

    public void neoSend(User user) {
        System.out.println("neoSender: " + user.getUsername());
        template.convertAndSend("neo", user);
    }

    public void send() {
        String context = "hi, fanout msg ";
        System.out.println("Sender : " + context);
        template.convertAndSend("fanoutExchange","", context);
    }

    public void send(String email) {
        System.out.println("Sender : " + email);
        template.convertAndSend("fanoutExchange","", email);
    }

    public void send(User user) {
        System.out.println("Sender : " + user.getUsername());
        template.convertAndSend("fanoutExchange","", user);
    }
}
