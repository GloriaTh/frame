package com.sboot.springboot.util;

import com.sboot.springboot.dao.UserMapper;
import com.sboot.springboot.domin.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "neo")
public class NeoReceiver1 {
    @Autowired
    private UserMapper userMapper;

    @RabbitHandler
    public void process(User user) {
        System.out.println("neoReceiver 1: " + user.getUsername());
        userMapper.insert(user);
        System.out.println("neoReceiver 1: over");
    }
}
