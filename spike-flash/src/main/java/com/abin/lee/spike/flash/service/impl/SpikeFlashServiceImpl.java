package com.abin.lee.spike.flash.service.impl;

import com.abin.lee.spike.flash.common.RedisUtil;
import com.abin.lee.spike.flash.common.SpikeLua;
import com.abin.lee.spike.flash.service.SpikeFlashService;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by abin on 2018/6/13.
 */
@Service
@Slf4j
public class SpikeFlashServiceImpl implements SpikeFlashService {
    private static final Integer total = 1000;

    @Resource
    RedisUtil jedis;

    @Override
    public void flash(String listName, String keyName) {
        while (true) {
            Jedis redis = jedis.getJedis();
            List<String> keys = Collections.singletonList(listName);
            keyName = (int) (Math.random() * 10000000L) + "";
            List<String> argvs = Arrays.asList(keyName);
            Object object = redis.eval(SpikeLua.spikeBySha, keys, argvs);

            if (object != null) {
                System.out.println("object : " + object);
            } else {
                //已经取完了
                Long len = redis.llen(listName);
                if (len > 5) {
                    log.info("current len= " + len);
                    break;
                }
            }
            jedis.returnResource(redis);
        }
    }

    @Override
    public void flashByLua(String listName, String keyName) throws IOException {
        while (true) {
            Jedis redis = jedis.getJedis();
            String spikePath = this.getClass().getClassLoader().getResource(SpikeLua.spikeLuaPath).getPath();
            String scriptLua = SpikeLua.loadScript(SpikeLua.spikeLuaPath);
            List<String> keys = Collections.singletonList(listName);
            keyName = (int) (Math.random() * 10000000L) + "";
            List<String> argvs = Collections.singletonList(keyName);
            Object object = redis.eval(scriptLua, keys, argvs);
            if (object != null) {
                System.out.println("object : " + object);
                //已经取完了
                Long len = redis.llen(listName);
                if (len > 5) {
                    log.info("current len= " + len);
                    break;
                }
            } else {
                //已经取完了
                Long len = redis.llen(listName);
                if (len > 5) {
                    log.info("current len= " + len);
                    break;
                }
            }
            jedis.returnResource(redis);
        }
    }

    @Override
    public void flashByScript(String listName, String keyName) {
        while (true) {
            Jedis redis = jedis.getJedis();
            List<String> keys = Collections.singletonList(listName);
            keyName = (int) (Math.random() * 10000000L) + "";
            List<String> argvs = Arrays.asList(keyName);
            Object object = redis.eval(SpikeLua.spikeBySha, keys, argvs);

            if (object != null) {
                System.out.println("object : " + object);
            } else {
                //已经取完了
                Long len = redis.llen(listName);
                if (len > 5) {
                    log.info("len=" + len);
                    break;
                }
            }
            jedis.returnResource(redis);
        }
    }


    @Override
    public void flashBySha(String listName, String keyName) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(total);
        System.err.println("start:" + System.currentTimeMillis() / 1000);
        for (int i = 0; i < total; ++i) {
            final int temp = i;
            Thread thread = new Thread() {
                public void run() {
                    while (true) {
                        Jedis redis = jedis.getJedis();
                        String sha = redis.scriptLoad(SpikeLua.spikeBySha);
                        List<String> keys = Collections.singletonList(listName);
                        String key = (int) (Math.random() * 10000000L) + "";
                        List<String> argvs = Arrays.asList(key);
//                        List<String> argvs = Arrays.asList(keyName);
                        Object object = redis.evalsha(sha, keys, argvs);
                        if (object != null) {
                            System.out.println("object : " + object);
                        } else {
                            //已经取完了
                            Long len = jedis.getJedis().llen(listName);
                            if (len > 5) {
                                log.info("len=" + len);
                                break;
                            }
                        }
                        jedis.returnResource(redis);
                    }
                    latch.countDown();
                }
            };
            thread.start();
        }

        latch.await();
        System.err.println("end:" + System.currentTimeMillis() / 1000);
    }


    @Override
    public Integer flashByShaScript(String listName, String keyName) throws InterruptedException {
        Jedis redis = jedis.getJedis();
        String sha = redis.scriptLoad(SpikeLua.spikeBySha);
        List<String> keys = Collections.singletonList(listName);
        String key = (int) (Math.random() * 10000000L) + "";
        List<String> argvs = Arrays.asList(key);
//      List<String> argvs = Arrays.asList(keyName);
        Object object = redis.evalsha(sha, keys, argvs);
        if (object != null) {
            Integer flashCount = (int) object;
            if (flashCount == 0)
                return 0;
            else
                return 1;
        } else {
            //已经取完了
            Long len = jedis.getJedis().llen(listName);
            if (len > 5)
                log.info("len=" + len);
        }
        jedis.returnResource(redis);
        return 1;
    }


    @Override
    public Integer limitFlow(String keyName, String limit, String limitTime) {
        Jedis redis = jedis.getJedis();
        String sha = redis.scriptLoad(SpikeLua.limitFlow);
        List<String> keys = Collections.singletonList(keyName);
        List<String> argvs = Arrays.asList(limit, limitTime);
        Object object = redis.evalsha(sha, keys, argvs);
        if (object != null) {
            Long flashCount = (Long) object;
            log.info("flashCount=" + flashCount);
            if (flashCount == 0)
                return 0;
            else
                return 1;
        } else {
            //已经取完了
            Long len = jedis.getJedis().llen(keyName);
            if (len > Ints.tryParse(limit))
                log.info("len=" + len);
        }
        jedis.returnResource(redis);
        return 1;
    }

    @Override
    public void limitFlowBatch(String keyName, String limit, String limitTime) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(total);
        System.err.println("start:" + System.currentTimeMillis() / 1000);
        for (int i = 0; i < total; ++i) {
            final int temp = i;
            Thread thread = new Thread() {
                public void run() {
                    while (true) {
                        Jedis redis = jedis.getJedis();
                        String sha = redis.scriptLoad(SpikeLua.limitFlow);
                        List<String> keys = Collections.singletonList(keyName);
                        List<String> argvs = Arrays.asList(limit, limitTime);
                        Object object = redis.evalsha(sha, keys, argvs);
                        if (object != null) {
                            Long flashCount = (Long) object;
                            log.info("flashCount=" + flashCount);
                        } else {
                            //已经取完了
                            Long len = jedis.getJedis().llen(keyName);
                            if (len > Longs.tryParse(limit)) {
                                log.info("len=" + len);
                                break;
                            }
                        }
                        jedis.returnResource(redis);
                    }
                    latch.countDown();
                }
            };
            thread.start();
        }
        latch.await();
    }


    @Override
    public void limitTrafficBatch(String keyName, String limit, String limitTime) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(total);
        System.err.println("start:" + System.currentTimeMillis() / 1000);
        for (int i = 0; i < total; ++i) {
            final int temp = i;
            Thread thread = new Thread() {
                public void run() {
                    while (true) {
                        Jedis redis = jedis.getJedis();
                        String sha = redis.scriptLoad(SpikeLua.limitTraffic);
                        String keyNames = System.currentTimeMillis() / 1000 + "";
                        List<String> keys = Collections.singletonList(keyNames);
                        List<String> argvs = Arrays.asList(limit, limitTime);
                        Object object = redis.evalsha(sha, keys, argvs);
                        if (object != null) {
                            Long flashCount = (Long) object;
                            log.info("flashCount=" + flashCount);
                        } else {
                            //已经取完了
                            Long len = jedis.getJedis().llen(keyNames);
                            if (len > Longs.tryParse(limit)) {
                                log.info("len=" + len);
                                break;
                            }
                        }
                        jedis.returnResource(redis);
                    }
                    latch.countDown();
                }
            };
            thread.start();
        }
        latch.await();
    }


}
