package com.sboot.springboot.util;

import com.sboot.springboot.common.MyJedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * @author WangJun
 * @className
 * @descripion
 * @date 2018年7月2日下午2:32:57
 */
@Component
public class JedisUtil {

    /**
     * @param key
     * @param value
     */
    public byte[] set(byte[] key, byte[] value) {
        // TODO Auto-generated method stub
        Jedis jedis = MyJedisConfig.getResource();
        try {
            jedis.set(key, value);
            return value;
        } finally {
            // TODO: handle finally clause
            jedis.close();
        }
    }

    /**
     * @param key
     * @param i
     */
    public void expire(byte[] key, int i) {
        Jedis jedis = MyJedisConfig.getResource();
        try {
            jedis.expire(key, i);
        } finally {
            // TODO: handle finally clause
            jedis.close();
        }

    }

    /**
     * @param key
     * @return
     */
    public byte[] get(byte[] key) {
        Jedis jedis = MyJedisConfig.getResource();
        try {
            return jedis.get(key);
        } finally {
            // TODO: handle finally clause
            jedis.close();
        }
    }

    /**
     * @param key
     */
    public void del(byte[] key) {
        // TODO Auto-generated method stub
        Jedis jedis = MyJedisConfig.getResource();
        try {
            jedis.del(key);
        } finally {
            // TODO: handle finally clause
            jedis.close();
        }
    }

    /**
     * @param sHIRO_SESSION_PREFIX
     * @return
     */
    public Set<byte[]> keys(String sHIRO_SESSION_PREFIX) {
        Jedis jedis = MyJedisConfig.getResource();
        try {
            return jedis.keys(sHIRO_SESSION_PREFIX.getBytes());
        } finally {
            // TODO: handle finally clause
            jedis.close();
        }
    }
}