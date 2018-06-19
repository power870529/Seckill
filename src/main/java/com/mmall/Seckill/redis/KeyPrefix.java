package com.mmall.Seckill.redis;

public interface KeyPrefix {

    // redis缓存中的有效时间
    int expireSeconds();

    // key的前缀
    String getPrefix();
}
