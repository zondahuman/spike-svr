package com.abin.lee.spike.distribute.test;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.recipes.queue.QueueSerializer;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Zookeeper - Curator - Queue - Distributed Queue
 * https://www.hifreud.com/2017/01/18/zookeeper-16-java-api-curator-07-queue/#distributed-queue%E5%88%86%E5%B8%83%E5%BC%8F%E9%98%9F%E5%88%97
 * @author Freud
 */
public class CuratorQueueDistributedQueueZookeeper {

    private static final int SECOND = 1000 ;
    private static final String path = "/curator_queue/distributed_queue";
    private static final CountDownLatch down = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            final int index = i;
            service.submit(new Callable<Void>() {
                public Void call() throws Exception {
                    new CuratorQueueDistributedQueueZookeeper().schedule(index);
                    return null;
                }
            });
        }
        down.countDown();

        Thread.sleep(10 * SECOND);
        service.shutdown();
    }

    private void schedule(final int index) throws Exception {
        down.await();
        CuratorFramework client = this.getStartedClient(index);
        // 创建队列
        DistributedQueue<String> queue = QueueBuilder.builder(client, new StringQueueConsumer(index),
                new StringQueueSerializer(), path).buildQueue();
        queue.start();
        if (index == 4) {
            Thread.sleep(3 * SECOND);
            for (int i = 0; i < 20; i++) {
                // 生产消息
                queue.put("message " + i);
            }
        }
    }

    private CuratorFramework getStartedClient(int index) {
        RetryPolicy rp = new ExponentialBackoffRetry(1 * SECOND, 5);
        // Fluent风格创建
        CuratorFramework cfFluent = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
//        CuratorFramework cfFluent = CuratorFrameworkFactory.builder().connectString("localhost:2181")
                .sessionTimeoutMs(5 * SECOND).connectionTimeoutMs(3 * SECOND).retryPolicy(rp).build();
        cfFluent.start();
        System.out.println("Thread [" + index + "] Server connected...");
        return cfFluent;
    }

    /**
     * 消息消费者
     *
     * @author Freud
     */
    public class StringQueueConsumer implements QueueConsumer<String> {

        private int index;

        public StringQueueConsumer(int index) {
            super();
            this.index = index;
        }

        public void stateChanged(CuratorFramework cf, ConnectionState state) {
        }

        public void consumeMessage(String message) throws Exception {
            System.out.println("Thread [" + index + "] get the queue value : " + message);
        }
    }

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
}