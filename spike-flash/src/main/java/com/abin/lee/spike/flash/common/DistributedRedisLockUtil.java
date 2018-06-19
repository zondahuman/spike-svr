package com.abin.lee.spike.flash.common;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * Created by abin on 2018/6/19.
 * https://blog.csdn.net/iamlihongwei/article/details/78727941
 */
@Component
public class DistributedRedisLockUtil {

    @Resource
    RedisUtil jedis;


    /**
     * 加锁
     *
     * @param lock
     * @param expired
     * @return
     */
    public boolean lockWithTimeout(String lock, long expired) {
        boolean success = false;
        Jedis redis = jedis.getJedis();
        // expired 单位为秒
        long value = System.currentTimeMillis() + expired * 1000 + 1;

        long acquired = redis.setnx(lock, String.valueOf(value));
        if (acquired == 1) {
            success = true;
        } else {
            String oldValueStr = redis.get(lock);
            if (StringUtils.isNotEmpty(oldValueStr)) {
                long oldValue = Long.valueOf(oldValueStr);
                if (oldValue < System.currentTimeMillis()) {
                    String getValue = redis.getSet(lock, String.valueOf(value));
                    if (Long.valueOf(getValue) == oldValue) {
                        success = true;
                    } else {
                        success = false;
                    }
                } else {
                    success = false;
                }
            }

        }
        return success;
    }


    /**
     * 释放锁
     *
     * @param lock
     */
    public void releaseLock(String lock) {
        long current = System.currentTimeMillis();
        Jedis redis = jedis.getJedis();
        String lockKey = redis.get(lock);
        if (!Strings.isNullOrEmpty(lockKey) && current < Long.valueOf(redis.get(lock))) {
            redis.del(lock);
        }
    }


}
