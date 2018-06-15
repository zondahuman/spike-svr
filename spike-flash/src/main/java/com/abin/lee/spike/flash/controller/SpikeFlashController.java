package com.abin.lee.spike.flash.controller;

import com.abin.lee.spike.flash.service.SpikeFlashService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by abin on 2018/6/13.
 */
@Controller
@RequestMapping("/spike")
@Slf4j
public class SpikeFlashController {


    @Autowired
    SpikeFlashService spikeFlashService;


    @RequestMapping(value = "/flash")
    @ResponseBody
    public String flash(String listName, String keyName) {
        String result = "FAILURE";
        try {
            this.spikeFlashService.flash(listName, keyName);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/flashByScript")
    @ResponseBody
    public String flashByScript(String listName, String keyName) {
        String result = "FAILURE";
        try {
            this.spikeFlashService.flashByScript(listName, keyName);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/flashBySha")
    @ResponseBody
    public String flashBySha(String listName, String keyName) {
        String result = "FAILURE";
        try {
            this.spikeFlashService.flashBySha(listName, keyName);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/flashByShaScript")
    @ResponseBody
    public String flashByShaScript(String listName, String keyName) {
        String result = "FAILURE";
        try {
            this.spikeFlashService.flashByShaScript(listName, keyName);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/limitFlow")
    @ResponseBody
    public String limitFlow(String keyName, String limit, String limitTime) {
        String result = "FAILURE";
        try {
            this.spikeFlashService.limitFlow(keyName, limit, limitTime);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/limitFlowBatch")
    @ResponseBody
    public String limitFlowBatch(String keyName, String limit, String limitTime) {
        String result = "FAILURE";
        try {
            this.spikeFlashService.limitFlowBatch(keyName, limit, limitTime);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/limitTrafficBatch")
    @ResponseBody
    public String limitTrafficBatch(String keyName, String limit, String limitTime) {
        String result = "FAILURE";
        try {
            this.spikeFlashService.limitTrafficBatch(keyName, limit, limitTime);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



}
