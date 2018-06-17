package com.abin.lee.spike.flash.common;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLBackgroundPathAndBytesable;
import org.apache.curator.framework.recipes.queue.SimpleDistributedQueue;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by abin on 2018/6/17.
 */
@Component
public class CuratorUtil {

    CuratorFramework curator = null ;

    @PostConstruct
    public void previous(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // Fluent风格创建
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString("localhost:2181")
                .sessionTimeoutMs(5 * 1000).connectionTimeoutMs(3 * 1000).retryPolicy(retryPolicy).build();
        curatorFramework.start();
    }

    public void put(String queueName, String message) throws Exception {
        SimpleDistributedQueue queue = new SimpleDistributedQueue(curator, queueName);
        queue.offer(("qn-" + message).getBytes());
    }

    public String get(String queueName) throws Exception {
        SimpleDistributedQueue queue = new SimpleDistributedQueue(curator, queueName);
        byte[] result = queue.take();
        String middle = new String(result, "UTF-8");
        return middle ;
    }


//    public boolean offer(byte[] data) throws Exception {
//        String thisPath = ZKPaths.makePath(this.path, "qn-");
//        ((ACLBackgroundPathAndBytesable)this.client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)).forPath(thisPath, data);
//        return true;
//    }






}
