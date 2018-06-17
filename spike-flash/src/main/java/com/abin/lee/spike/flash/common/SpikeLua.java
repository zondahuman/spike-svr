package com.abin.lee.spike.flash.common;

import com.google.common.io.CharStreams;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * lua+redis 入队操作，；lua保证redis操作的原子性
 */

public class SpikeLua {


    /**
     * lua队列入队操作 . 秒杀设计
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


    public static String limitFlow = "local key = \"rate.limit:\" .. KEYS[1]\n" +
            "local limit = tonumber(ARGV[1])\n" +
            "local expire_time = ARGV[2]\n" +
            "\n" +
            "local is_exists = redis.call(\"EXISTS\", key)\n" +
            "if is_exists == 1 then\n" +
            "    local num = redis.call(\"GET\", key)\n" +
            "    local totalLimit = tonumber(num)\n" +
            "    if totalLimit > limit then\n" +
            "        return 0\n" +
            "    else\n" +
            "        redis.call(\"INCR\", key)\n" +
            "        return 1\n" +
            "    end\n" +
            "else\n" +
            "    redis.call(\"SET\", key, 1)\n" +
            "    redis.call(\"EXPIRE\", key, expire_time)\n" +
            "    return 1\n" +
            "end";

    public static String limitTraffic = "local key = \"rate.limit:\" .. KEYS[1]\n" +
            "local limit = tonumber(ARGV[1])\n" +
            "local expire_time = ARGV[2]\n" +
            "\n" +
            "local is_exists = redis.call(\"EXISTS\", key)\n" +
            "if is_exists == 1 then\n" +
            "    if redis.call(\"INCR\", key) > limit then\n" +
            "        return 0\n" +
            "    else\n" +
            "        return 1\n" +
            "    end\n" +
            "else\n" +
            "    redis.call(\"SET\", key, 1)\n" +
            "    redis.call(\"EXPIRE\", key, expire_time)\n" +
            "    return 1\n" +
            "end";


//    public static String spikeLuaPath = "limit/spike.lua";
    public static String spikeLuaPath = "lua/spike.lua";


    // 加载Lua代码
    public static String loadScript(String fileName) throws IOException {
        Reader reader = new InputStreamReader(Client.class.getClassLoader().getResourceAsStream(fileName));
        return CharStreams.toString(reader);
    }


    public static String getPath(String param){
        String path = SpikeLua.class.getClassLoader().getResource(param).getPath();
//        String path = this.getClass().getClassLoader().getResource(spikeLua).getPath();
        return path;
    }

    public void min(){
        String spike = SpikeLua.class.getClassLoader().getResource(spikeLuaPath).getPath();
        String spike2 = this.getClass().getClassLoader().getResource(spikeLuaPath).getPath();
        URL spike1 = this.getClass().getClassLoader().getResource("/");
        System.out.println(spike);
        System.out.println(spike1);
        System.out.println(spike2);
    }

    public static void main(String[] args) {
        new SpikeLua().min();
    }

}