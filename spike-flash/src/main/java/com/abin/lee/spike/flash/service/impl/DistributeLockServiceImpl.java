package com.abin.lee.spike.flash.service.impl;

import com.abin.lee.spike.flash.common.*;
import com.abin.lee.spike.flash.service.DistributeLockService;
import com.abin.lee.spike.flash.service.DistributeQueueService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
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
public class DistributeLockServiceImpl implements DistributeLockService {

    @Resource
    CuratorDisLockUtil disLock;
    @Resource
    DistributedRedisLockUtil jedis;


    @Override
    public InterProcessMutex createZkDisLock(String lockPath) throws Exception {
        InterProcessMutex lock = disLock.acquire(lockPath);
        return lock;
    }

    @Override
    public void releaseZkDisLock(InterProcessMutex lock) throws Exception {
        disLock.release(lock);
    }

    @Override
    public void createRedisDisLock(String lockName) throws Exception {
        jedis.lockWithTimeout(lockName, 60000);
    }

    @Override
    public void releaseRedisDisLock(String lockName) throws Exception {
        jedis.releaseLock(lockName);
    }







}
