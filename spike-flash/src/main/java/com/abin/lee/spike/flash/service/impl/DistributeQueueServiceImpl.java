package com.abin.lee.spike.flash.service.impl;

import com.abin.lee.spike.flash.common.CuratorUtil;
import com.abin.lee.spike.flash.service.DistributeQueueService;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;

import javax.annotation.Resource;

/**
 * Created by abin on 2018/6/17.
 */
public class DistributeQueueServiceImpl implements DistributeQueueService {

    @Resource
    CuratorUtil curator ;

    @Override
    public void redisQueue(String message) {

    }

    @Override
    public void zkPutQueue(String queueName, String message) throws Exception {
        curator.put(queueName, message);
    }

    @Override
    public String zkGetQueue(String queueName) throws Exception {
        String result = curator.get(queueName);
        return result;
    }



}
