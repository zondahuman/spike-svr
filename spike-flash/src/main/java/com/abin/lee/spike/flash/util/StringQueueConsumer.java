package com.abin.lee.spike.flash.util;

/**
 * Created by abin on 2018/6/18.
 */

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.state.ConnectionState;

/**
 * 消息消费者
 *
 * @author Freud
 */
//@Component
public class StringQueueConsumer implements QueueConsumer<String> {

    public StringQueueConsumer() {
        super();
    }

    public void stateChanged(CuratorFramework cf, ConnectionState state) {
    }

    public void consumeMessage(String message) throws Exception {
        System.out.println("Thread [] get the queue value : " + message);
    }
}
