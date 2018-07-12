package com.sboot.springboot.web;

import java.util.Collection;
import java.util.Set;

import com.sboot.springboot.util.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

@Component
public class RedisCache<K, V> implements Cache<K, V> {
    private String CACHE_PREFIX = "cache:";

    public byte[] getKey(K k) {
        if (k instanceof String) {
            return (CACHE_PREFIX + k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public void clear() throws CacheException {
        // TODO Auto-generated method stub

    }

    @Override
    public V get(K arg0) throws CacheException {
        // TODO Auto-generated method stub
        byte[] value = jedisUtil.get(getKey(arg0));
        if (value != null) {
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
}

    @Override
    public Set<K> keys() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public V put(K arg0, V arg1) throws CacheException {
        // TODO Auto-generated method stub
        byte[] key = getKey(arg0);
        byte[] value = SerializationUtils.serialize(arg1);
        jedisUtil.set(key, value);
        jedisUtil.expire(key, 600);
        return arg1;
    }

    @Override
    public V remove(K arg0) throws CacheException {
        // TODO Auto-generated method stub
        byte[] key = getKey(arg0);
        byte[] value = jedisUtil.get(key);
        jedisUtil.del(key);
        if (value != null) {
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Collection<V> values() {
        // TODO Auto-generated method stub
        return null;
    }

}