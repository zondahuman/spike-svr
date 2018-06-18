package com.abin.lee.spike.flash.common;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by abin on 2018/6/17.
 */
@Component
public class CuratorDisLockUtil {

    public CuratorFramework client = null ;
    public InterProcessMutex lock =  null ;

    @PostConstruct
    public void previous(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // Fluent风格创建
        client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
                .sessionTimeoutMs(5 * 1000).connectionTimeoutMs(3 * 1000).retryPolicy(retryPolicy).build();
        client.start();
        lock = new InterProcessMutex(client, "/super");
    }


    public void acquire() throws Exception {
        lock.acquire();
    }

    public void release() throws Exception {
        lock.release();
    }


//    public boolean offer(byte[] data) throws Exception {
//        String thisPath = ZKPaths.makePath(this.path, "qn-");
//        ((ACLBackgroundPathAndBytesable)this.client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)).forPath(thisPath, data);
//        return true;
//    }






}
