package com.hhx.hystrix.controller;

import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixThreadPoolMetrics;
import com.netflix.hystrix.util.HystrixRollingNumberEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author hhx
 */
@Slf4j
@RestController
public class HystrixMonitor {

    @GetMapping("/metrics")
    public void metrics() {
        Collection<HystrixCommandMetrics> commandMetricsList = HystrixCommandMetrics
                .getInstances();
        for (HystrixCommandMetrics metrics : commandMetricsList) {
            log.info("---------------------------------------------------------------------------");
            log.info("getCommandGroup : {}", metrics.getCommandGroup().name());
            log.info("getCommandKey : {}", metrics.getCommandKey().name());
            log.info("getThreadPoolKey : {}", metrics.getThreadPoolKey());
            log.info("getCumulativeCount : {}", metrics.getCumulativeCount(HystrixEventType.SUCCESS));
            log.info("getCumulativeCount : {}", metrics.getCumulativeCount(HystrixRollingNumberEvent.SUCCESS));
            log.info("getCurrentConcurrentExecutionCount : {}", metrics.getCurrentConcurrentExecutionCount());
            log.info("getRollingCount : {}", metrics.getRollingCount(HystrixEventType.SUCCESS));
            log.info("getRollingCount : {}", metrics.getRollingCount(HystrixRollingNumberEvent.SUCCESS));
            log.info("getRollingMaxConcurrentExecutions : {}", metrics.getRollingMaxConcurrentExecutions());
            log.info("getExecutionTimeMean : {}", metrics.getExecutionTimeMean());
            log.info("getExecutionTimePercentile : {}", metrics.getExecutionTimePercentile(100));
            log.info("getTotalTimePercentile : {}", metrics.getTotalTimePercentile(100));
            log.info("getHealthCounts : {}", metrics.getHealthCounts().toString());
            log.info("circuitBreakerEnabled : {}", metrics.getProperties().circuitBreakerEnabled().get());
        }
        Collection<HystrixThreadPoolMetrics> threadPoolMetricsList = HystrixThreadPoolMetrics.getInstances();
        for (HystrixThreadPoolMetrics metrics : threadPoolMetricsList) {
            log.info("---------------------------------------------------------------------------");
            log.info("getThreadPoolKey : {}", metrics.getThreadPoolKey().name());
            log.info("getCurrentCorePoolSize : {}", metrics.getCurrentCorePoolSize());
            log.info("getCurrentMaximumPoolSize : {}", metrics.getCurrentMaximumPoolSize());
            log.info("getCurrentActiveCount : {}", metrics.getCurrentActiveCount());
            log.info("getCurrentLargestPoolSize : {}", metrics.getCurrentLargestPoolSize());
            log.info("getCurrentTaskCount : {}", metrics.getCurrentTaskCount());
            log.info("getCurrentQueueSize : {}", metrics.getCurrentQueueSize());
            log.info("getCurrentCompletedTaskCount : {}", metrics.getCurrentCompletedTaskCount());
            log.info("getCumulativeCountThreadsExecuted : {}", metrics.getCumulativeCountThreadsExecuted());
            log.info("getCumulativeCountThreadsRejected : {}", metrics.getCumulativeCountThreadsRejected());
            log.info("getRollingCountThreadsExecuted : {}", metrics.getRollingCountThreadsExecuted());
            log.info("getRollingCountThreadsRejected : {}", metrics.getRollingCountThreadsRejected());
            log.info("getRollingMaxActiveThreads : {}", metrics.getRollingMaxActiveThreads());
        }
    }
}
