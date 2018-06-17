package com.abin.lee.spike.flash.service;

import java.io.IOException;

/**
 * Created by abin on 2018/6/13.
 */
public interface SpikeFlashService {

    void flash(String listName, String keyName);

    void flashByLua(String listName, String keyName) throws IOException;

    void flashByScript(String listName, String keyName);

    void flashBySha(String listName, String keyName) throws InterruptedException ;

    Integer flashByShaScript(String listName, String keyName) throws InterruptedException ;

    Integer limitFlow(String keyName, String limit, String limitTime) ;

    void limitFlowBatch(String keyName, String limit, String limitTime) throws InterruptedException;

    void limitTrafficBatch(String keyName, String limit, String limitTime) throws InterruptedException;


}
