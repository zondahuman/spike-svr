package com.abin.lee.spike.flash.common;

import com.abin.lee.spike.flash.util.StringQueueConsumer;
import com.abin.lee.spike.flash.util.StringQueueSerializer;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.SimpleDistributedQueue;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by abin on 2018/6/18.
 */
@Component
public class DistributeQueueUtil {

    CuratorFramework client = null ;
    DistributedQueue<String> queue = null ;
    String path = "/disq/queue" ;

    @PostConstruct
    public void previous() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // Fluent风格创建
        client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
                .sessionTimeoutMs(5 * 1000).connectionTimeoutMs(3 * 1000).retryPolicy(retryPolicy).build();
        client.start();
        // 创建队列
        queue = QueueBuilder.builder(client, null,
                new StringQueueSerializer(), path).buildQueue();
//        queue = QueueBuilder.builder(client, new StringQueueConsumer(),
//                new StringQueueSerializer(), path).buildQueue();
        queue.start();
    }

    public void put(String message) throws Exception {
        queue.put(message);
    }



    public void create(String queueName) throws Exception {
        if (client.checkExists().forPath(queueName) != null) {
            client.delete().deletingChildrenIfNeeded().forPath(queueName);
        }
    }



//    public boolean offer(byte[] data) throws Exception {
//        String thisPath = ZKPaths.makePath(this.path, "qn-");
//        ((ACLBackgroundPathAndBytesable)this.client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)).forPath(thisPath, data);
//        return true;
//    }






}
