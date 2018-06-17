package com.abin.lee.spike.flash.service;

/**
 * Created by abin on 2018/6/17.
 */
public interface DistributeQueueService {

    void redisQueue(String message);

    void zkPutQueue(String queueName, String message) throws Exception ;

    String zkGetQueue(String queueName) throws Exception ;















}
