package com.sboot.springboot.common;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMQConfig {
    private static CachingConnectionFactory connectionFactory;

    public String host;
    public Integer port;
    public String username;
    public String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    @Bean(name = "firstConnectionFactory")
//    public CachingConnectionFactory firstConnectionFactory() {
//        connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setHost(this.host);
//        connectionFactory.setPort(this.port);
//        connectionFactory.setUsername(this.username);
//        connectionFactory.setPassword(this.password);
//        return connectionFactory;
//    }
//
//    @Bean
//    public String firstQueue() {
//        System.out.println("configuration firstQueue ........................");
//        try {
//            connectionFactory.createConnection().createChannel(true).queueDeclare("hello", false, false, false, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            return "firstQueue";
//        }
//    }

//    @Bean
//    public Queue Queue() {
//        return new Queue("hello");
//    }

    @Bean
    public Queue Queue2() {
        return new Queue("neo");
    }

    @Bean
    public Queue AMessage() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue BMessage() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue CMessage() {
        return new Queue("fanout.C");
    }

}
