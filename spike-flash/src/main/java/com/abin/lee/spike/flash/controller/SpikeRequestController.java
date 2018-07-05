package com.abin.lee.spike.flash.controller;

import com.abin.lee.spike.common.util.JsonUtil;
import com.abin.lee.spike.flash.model.SpikeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by abin on 2018/6/17.
 * https://www.hifreud.com/2017/01/18/zookeeper-16-java-api-curator-07-queue/#distributed-queue%E5%88%86%E5%B8%83%E5%BC%8F%E9%98%9F%E5%88%97
 */

@Controller
@RequestMapping("/spikeRequest")
@Slf4j
public class SpikeRequestController {


    @PatchMapping(value = "/spikePatch")
    @ResponseBody
    public String spikePatch(SpikeModel spikeModel) {
        String result = "FAILURE";
        try {
            System.out.println("-----------------------------------------------");
            System.out.println("spikeModel----" + JsonUtil.toJson(spikeModel));
            System.out.println("-----------------------------------------------");
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }






}
