package com.hmblogs.backend.util;

import redis.clients.jedis.Jedis;

public class JedisUtil {
    public static Jedis getJedisConn(){
        Jedis jedisCom = new Jedis("localhost",6379);
        jedisCom.auth("heming");
        return jedisCom;
    }
}
