package com.sboot.springboot.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

@Component
@ConfigurationProperties(prefix = "spring.redis")
public class MyJedisConfig {
    private static JedisPool pool;
    public String host;
    public Integer port;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @PostConstruct
    private void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        pool = new JedisPool(config, host, port, 1000 * 2, password);
    }

    public static Jedis getResource() {
        return pool.getResource();
    }
}
