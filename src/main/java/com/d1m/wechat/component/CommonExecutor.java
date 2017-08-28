package com.d1m.wechat.component;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

/**
 * CommonS
 *
 * @author f0rb on 2017-03-16.
 */
@Slf4j
@Component
public class CommonExecutor {
    @Async("callerRunsExecutor")
    public <V> Future<V> call(Callable<V> callable) {
        try {
            return new AsyncResult<>(callable.call());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Async("callerRunsExecutor")
    public void run(Runnable runnable) {
        runnable.run();
    }
}
