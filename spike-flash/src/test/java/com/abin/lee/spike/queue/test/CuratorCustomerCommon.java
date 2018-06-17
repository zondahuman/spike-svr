package com.abin.lee.spike.queue.test;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.queue.SimpleDistributedQueue;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by abin on 2018/6/17.
 */
public class CuratorCustomerCommon {

    CuratorFramework curator = null ;

    {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // Fluent风格创建
        curator = CuratorFrameworkFactory.builder().connectString("localhost:2181")
                .sessionTimeoutMs(5 * 1000).connectionTimeoutMs(3 * 1000).retryPolicy(retryPolicy).build();
        curator.start();
    }

    public void put(String queueName, String message) throws Exception {
        SimpleDistributedQueue queue = new SimpleDistributedQueue(curator, queueName);
        queue.offer(message.getBytes());
    }

    public String get(String queueName) throws Exception {
        SimpleDistributedQueue queue = new SimpleDistributedQueue(curator, queueName);
        byte[] result = queue.take();
        String middle = new String(result, "UTF-8");
        return middle ;
    }

    public void create(String queueName) throws Exception {
        if (curator.checkExists().forPath(queueName) != null) {
            curator.delete().deletingChildrenIfNeeded().forPath(queueName);
        }
    }

    public static void main(String[] args) throws Exception {
        CuratorCustomerCommon curatorCommon = new CuratorCustomerCommon();
        String queueName = "/lee1";

        while(true){
            String result = curatorCommon.get(queueName);
            System.out.println("result=" + result);
        }


    }






}
