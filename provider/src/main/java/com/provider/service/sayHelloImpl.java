package com.provider.service;

public class sayHelloImpl implements sayHello {
    @Override
    public String sayHello(String msg) {
        System.out.println(msg);
        return msg;
    }
}
