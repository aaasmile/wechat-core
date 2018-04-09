package com.d1m.wechat.mapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.d1m.wechat.test.SpringTest;

/**
 * MemberMapperTest
 *
 * @author f0rb on 2016-12-22.
 * @since 1.0.5
 */
public class MemberMapperTest extends SpringTest {
	
	private static final Logger log = LoggerFactory.getLogger(MemberMapperTest.class);
	
    @Resource
    private MemberMapper memberMapper;

    private Integer wechatId = 2;


    private void bathInsert(int total) {
        bathInsert(total, "o6MLQst24Av7v7BE_");
    }
    private void bathInsert(int total, String prefix) {
        List<String> list = new ArrayList<>(total + 1);
        for (int i = 0; i < total; i++) {
            list.add(prefix + String.format("%04d", i));
        }

        long before = System.currentTimeMillis();
        int count = memberMapper.batchInsertOpenId(wechatId, list);
        long after = System.currentTimeMillis();

        log.info("批量插入{}条openid, 实际插入{}条, 用时: {}ms", total, count, after - before);
    }

    @Test
    public void batchInsertOpenId10K() throws Exception {
        bathInsert(10000);
    }

    @Test
    //@Rollback(false)
    public void batchInsertOpenId_5_15_100() throws Exception {
        bathInsert(5);
        bathInsert(15);
        bathInsert(100);
    }

    @Test
    @Ignore
    public void batchInsertOpenId100W() {
        //模拟100万个用户的同步
        int count = 100;
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                5, 50, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(2000),
                new ThreadPoolExecutor.CallerRunsPolicy());

        final int size = 10000;

        List<Future<String>> collector = new LinkedList<>();

        long start = System.currentTimeMillis();
        for (int num = 0; num < count; num++) {

            //模拟拉取微信10000条openid. 串行
            final List<String> list = new ArrayList<>(size + 1);
            String prefix = "o6MLQst24A_" + String.format("%02d_", num);
            for (int i = 0; i < size; i++) {
                list.add(prefix + String.format("%04d", i));
            }

            //批量插入. 并行
            Future<String> future = pool.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {

                    long before = System.currentTimeMillis();
                    int count = memberMapper.batchInsertOpenId(wechatId, list);
                    long after = System.currentTimeMillis();

                    String result = String.format("批量插入%d条openid, 实际插入%d条, 用时: %dms", size, count, after - before);
                    log.info(result);
                    return result;
                }
            });
            collector.add(future);
        }

        List<String> results = new LinkedList<>();
        for (Future<String> future : collector) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        log.info("总耗时: {}ms", System.currentTimeMillis() - start);

        System.out.println(results.size());
    }

}