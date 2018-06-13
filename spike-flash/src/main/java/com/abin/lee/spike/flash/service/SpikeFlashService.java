package com.abin.lee.spike.flash.service;

/**
 * Created by abin on 2018/6/13.
 */
public interface SpikeFlashService {

    void flash(String listName, String keyName);

    void flashByScript(String listName, String keyName);

    void flashBySha(String listName, String keyName) throws InterruptedException ;

    Integer flashByShaScript(String listName, String keyName) throws InterruptedException ;

}
