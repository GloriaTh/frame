package com.sboot.springboot.util;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello")
public class RabbitReceiver {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }
//    @RabbitHandler
//    public void process(String message) {
//        System.out.println("fanout Receiver A  : " + message);
//    }

}