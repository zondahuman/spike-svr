package com.abin.lee.spike.flash.util;

/**
 * Created by abin on 2018/6/18.
 */

import org.apache.curator.framework.recipes.queue.QueueSerializer;

/**
 * 消息序列化和反序列化逻辑
 *
 * @author Freud
 */
public class StringQueueSerializer implements QueueSerializer<String> {

    public byte[] serialize(String item) {
        return item.getBytes();
    }

    public String deserialize(byte[] bytes) {
        return new String(bytes);
    }
}