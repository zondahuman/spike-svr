package com.abin.lee.spike.flash.service;

/**
 * Created by abin on 2018/6/17.
 */
public interface DistributeLockService {

    void createZkDisLock(String zkPath) throws Exception;


    void releaseZkDisLock(String zkPath) throws Exception;


    void createRedisDisLock(String zkPath) throws Exception;


    void releaseRedisDisLock(String zkPath) throws Exception;











}
