package com.abin.lee.spike.flash.service.impl;

import com.abin.lee.spike.flash.common.CuratorUtil;
import com.abin.lee.spike.flash.common.DistributeLua;
import com.abin.lee.spike.flash.common.RedisUtil;
import com.abin.lee.spike.flash.common.SpikeLua;
import com.abin.lee.spike.flash.service.DistributeQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by abin on 2018/6/17.
 */
@Service
@Slf4j
public class DistributeQueueServiceImpl implements DistributeQueueService {

    @Resource
    CuratorUtil curator;
    @Resource
    RedisUtil jedis;


    @Override
    public Long redisPutQueue(String queueName, String keyName) {
        Jedis redis = jedis.getJedis();
        List<String> keys = Collections.singletonList(queueName);
        List<String> argvs = Arrays.asList(keyName);
        Object object = redis.eval(DistributeLua.distributeBySha, keys, argvs);

        if (object != null) {
            System.out.println("object : " + object);
            return (Long)object;
        } else {
            //已经取完了
            Long len = redis.llen(queueName);
            if (len > 5) {
                log.info("current len= " + len);
            }
        }
        jedis.returnResource(redis);
        return 0L;
    }

    @Override
    public String redisGetQueue(String queueName, String destinationQueueName) throws Exception {
        Jedis redis = jedis.getJedis();
        String result = redis.brpoplpush(queueName, destinationQueueName, 6000) ;
        return result ;
    }

    @Override
    public void zkPutQueue(String queueName, String keyName) throws Exception {
        curator.put(queueName, keyName);
    }

    @Override
    public String zkGetQueue(String queueName) throws Exception {
        String result = curator.get(queueName);
        return result;
    }


}
