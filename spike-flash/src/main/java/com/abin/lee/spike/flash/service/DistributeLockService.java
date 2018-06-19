package com.abin.lee.spike.flash.service;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**
 * Created by abin on 2018/6/17.
 */
public interface DistributeLockService {

    InterProcessMutex createZkDisLock(String lockPath) throws Exception;


    void releaseZkDisLock(InterProcessMutex lock) throws Exception;


    void createRedisDisLock(String zkPath) throws Exception;


    void releaseRedisDisLock(String zkPath) throws Exception;











}
