package com.abin.lee.spike.flash.controller;

import com.abin.lee.spike.flash.service.DistributeQueueService;
import com.abin.lee.spike.flash.service.SpikeFlashService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by abin on 2018/6/17.
 * https://www.hifreud.com/2017/01/18/zookeeper-16-java-api-curator-07-queue/#distributed-queue%E5%88%86%E5%B8%83%E5%BC%8F%E9%98%9F%E5%88%97
 */

@Controller
@RequestMapping("/dqueue")
@Slf4j
public class DistributeQueueController {


    @Autowired
    DistributeQueueService distributeQueueService;


    @RequestMapping(value = "/zkPutQueue")
    @ResponseBody
    public String zkPutQueue(String queueName, String message) {
        String result = "FAILURE";
        try {
            this.distributeQueueService.zkPutQueue(queueName, message);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/zkGetQueue")
    @ResponseBody
    public String zkGetQueue(String queueName) {
        String result = "";
        try {
            result = this.distributeQueueService.zkGetQueue(queueName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }













}
