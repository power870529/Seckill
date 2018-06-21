package com.mmall.Seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            String realKey = prefix.getPrefix() + key;
            String string = jedis.get(realKey);
            T t = stringToBean(string, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String string = beanToString(value);
            if(string == null || string.length() <= 0) {
                return false;
            }

            String realKey = prefix.getPrefix() + key;
            int seconds = prefix.expireSeconds();
            if(seconds <= 0) {
                jedis.set(realKey, string);
            } else {
                jedis.setex(realKey, seconds, string);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean exits (KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> Long incr (KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> Long decr (KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }

        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if(clazz == String.class) {
            return (String)value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }

    }

    private <T> T stringToBean(String string, Class clazz) {
        if(string == null || string.length() <= 0 || clazz == null) {
            return null;
        }

        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(string);
        } else if(clazz == String.class) {
            return (T)string;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T)Long.valueOf(string);
        } else {
            return (T)JSON.toJavaObject(JSON.parseObject(string), clazz);
        }

    }

    private void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }
}
