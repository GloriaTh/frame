package com.sboot.springboot.service.impl;

import com.provider.service.sayHello;
import org.springframework.stereotype.Service;

@Service("Hello")
public class sayHelloImpl implements sayHello {

    @Override
    public String sayHello(String msg) {
        System.out.println(msg);
        return msg;
    }
}
