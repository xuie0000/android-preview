package xuk.util;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jie Xu
 * @date 2018/9/11
 */
public class TimedTask {
    public static void useScheduledThreadPoolExecutorImplTimedTask(long initialDelay, long period, Runnable runnable) {
        //org.apache.commons.lang3.concurrent.BasicThreadFactory
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        executorService.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.SECONDS);
    }
}
