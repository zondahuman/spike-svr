package com.abin.lee.spike.flash.common;

import com.google.common.io.CharStreams;
import redis.clients.jedis.Client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * lua+redis 入队操作，；lua保证redis操作的原子性
 */

public class DistributeLua {


    /**
     * lua队列入队操作 . 秒杀设计
     */
    public static String distributeBySha = "local totalLen = redis.call('llen', KEYS[1]) --查询队列的长度\n" +
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