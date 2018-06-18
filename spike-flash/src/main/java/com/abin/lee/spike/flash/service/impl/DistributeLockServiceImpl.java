package com.abin.lee.spike.flash.service.impl;

import com.abin.lee.spike.flash.common.*;
import com.abin.lee.spike.flash.service.DistributeLockService;
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
public class DistributeLockServiceImpl implements DistributeLockService {

    @Resource
    CuratorDisLockUtil disLock;
    @Resource
    RedisUtil jedis;


    @Override
    public void createZkDisLock(String zkPath) throws Exception {
      disLock.acquire();
    }





}
