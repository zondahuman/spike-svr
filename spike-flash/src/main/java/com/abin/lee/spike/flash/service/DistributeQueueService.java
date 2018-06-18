package com.abin.lee.spike.flash.service;

/**
 * Created by abin on 2018/6/17.
 */
public interface DistributeQueueService {

    Long redisPutQueue(String queueName, String keyName) ;

    String redisGetQueue(String queueName, String destinationQueueName) throws Exception ;

    void zkPutQueue(String queueName, String message) throws Exception ;

    String zkGetQueue(String queueName) throws Exception ;















}
