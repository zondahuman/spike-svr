package com.abin.lee.spike.flash.common;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * lua+redis 入队操作，；lua保证redis操作的原子性
 */

public class SpikeLua {


    /**
     * lua队列入队操作 .
     */
    public static String spikeBySha = "local totalLen = redis.call('llen', KEYS[1]) --查询队列的长度\n" +
            "\n" +
            "if totalLen >= 5 then\n" +
            "    return 0\n" +
            "end\n" +
            "\n" +
            "local currentLen = redis.call('lpush', KEYS[1], ARGV[1])\n" +
            "\n" +
            "if currentLen >= 5 then\n" +
            "    return 0\n" +
            "end\n" +
            "\n" +
            "return 1 ";





}