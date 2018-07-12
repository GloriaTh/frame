package com.sboot.springboot.web;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.sboot.springboot.util.JedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

/**
 * @className
 * @descripion
 * @author WangJun
 * @date 2018年7月2日下午2:32:20
 */
public class RedisSessionDao extends AbstractSessionDAO {
    @Autowired
    private JedisUtil jedisUtil;

    private final String SHIRO_SESSION_PREFIX ="session";

    private byte[] getKey(String key) {
        return (SHIRO_SESSION_PREFIX+key).getBytes();
    }

    private void saveSession(Session session) {
        if(session != null && session.getId() != null) {
            byte[] key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisUtil.set(key,value);
            jedisUtil.expire(key,600);
        }
    }
    @Override
    public void update(Session session) throws UnknownSessionException {
        // TODO Auto-generated method stub
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        // TODO Auto-generated method stub
        if(session == null || session.getId() ==null) {
            return;
        }
        byte[]  key = getKey(session.getId().toString());
        jedisUtil.del(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys= jedisUtil.keys(SHIRO_SESSION_PREFIX);
        Set<Session> sessions = new HashSet<Session>();
        if(CollectionUtils.isEmpty(keys)) {
            return sessions;
        }
        for(byte[] key : keys) {
            Session session = (Session) SerializationUtils.deserialize(jedisUtil.get(key));
            sessions.add(session);
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        // TODO Auto-generated method stub
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        // TODO Auto-generated method stub
        if(sessionId == null) {
            return null;
        }
        System.out.println("read session");
        byte[] key = getKey(sessionId.toString());
        byte[] value = jedisUtil.get(key);
        return (Session)SerializationUtils.deserialize(value);
    }

}