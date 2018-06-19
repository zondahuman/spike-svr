package com.abin.lee.spike.flash.controller;

import com.abin.lee.spike.flash.service.DistributeLockService;
import com.abin.lee.spike.flash.service.DistributeQueueService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by abin on 2018/6/17.
 * https://www.hifreud.com/2017/01/18/zookeeper-16-java-api-curator-07-queue/#distributed-queue%E5%88%86%E5%B8%83%E5%BC%8F%E9%98%9F%E5%88%97
 */

@Controller
@RequestMapping("/disLock")
@Slf4j
public class DistributeLockController {


    @Resource
    DistributeLockService distributeQueueService;


    @RequestMapping(value = "/createZkDisLock")
    @ResponseBody
    public String zkPutQueue(String lockPath) {
        String result = "FAILURE";
        try {
            this.distributeQueueService.createZkDisLock(lockPath);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/releaseZkDisLock")
    @ResponseBody
    public String releaseZkDisLock(InterProcessMutex lock) {
        String result = "FAILURE";
        try {
            this.distributeQueueService.releaseZkDisLock(lock);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/createRedisDisLock")
    @ResponseBody
    public String createRedisDisLock(String zkPath) {
        String result = "FAILURE";
        try {
            this.distributeQueueService.createRedisDisLock(zkPath);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/releaseRedisDisLock")
    @ResponseBody
    public String releaseRedisDisLock(String zkPath) {
        String result = "FAILURE";
        try {
            this.distributeQueueService.releaseRedisDisLock(zkPath);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }








}
