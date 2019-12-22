package com.hhx.hystrix.controller;

import com.netflix.hystrix.*;
import com.netflix.hystrix.metric.HystrixThreadPoolCompletionStream;
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


    /**
     * localhost:8080/metrics
     */
    @GetMapping("/metrics")
    public void metrics() {
        metricsCollapser();
        metricsCommand();
        metricsPool();
    }

    /**
     * localhost:8080/metrics/collapser
     */
    @GetMapping("/metrics/collapser")
    public void metricsCollapser() {
        log.info("----------------------------       metricsCollapser       ----------------------------------");
        Collection<HystrixCollapserMetrics> collapserMetrics = HystrixCollapserMetrics.getInstances();
        for (HystrixCollapserMetrics metrics : collapserMetrics) {
            log.info("---------------------------------------------------------------------------");
            log.info("getCollapserKey : {}", metrics.getCollapserKey().name());
        }
    }

    /**
     * localhost:8080/metrics/command
     */
    @GetMapping("/metrics/command")
    public void metricsCommand() {
        log.info("----------------------------       metricsCommand       ----------------------------------");
        Collection<HystrixCommandMetrics> commandMetricsList = HystrixCommandMetrics.getInstances();
        for (HystrixCommandMetrics metrics : commandMetricsList) {
            log.info("---------------------------------------------------------------------------");
            log.info("getCommandGroup : {}", metrics.getCommandGroup().name());
            log.info("getCommandKey : {}", metrics.getCommandKey().name());
            log.info("getThreadPoolKey : {}", metrics.getThreadPoolKey());
            for (HystrixEventType event : HystrixEventType.values()) {
                log.info("getCumulativeCount HystrixEventType {} : {}", event, metrics.getCumulativeCount(event));
                log.info("getRollingCount HystrixEventType {} : {}", event, metrics.getRollingCount(event));
            }
            for (HystrixRollingNumberEvent event : HystrixRollingNumberEvent.values()) {
                try {
                    log.info("getCumulativeCount HystrixRollingNumberEvent {} : {}", event, metrics.getCumulativeCount(event));
                    log.info("getRollingCount HystrixRollingNumberEvent {} : {}", event, metrics.getRollingCount(event));
                } catch (Exception e) {
                   log.warn(e.getMessage());
                }
            }
            log.info("getCurrentConcurrentExecutionCount : {}", metrics.getCurrentConcurrentExecutionCount());
            log.info("getRollingMaxConcurrentExecutions : {}", metrics.getRollingMaxConcurrentExecutions());
            log.info("getExecutionTimeMean : {}", metrics.getExecutionTimeMean());
            log.info("getExecutionTimePercentile : {}", metrics.getExecutionTimePercentile(100));
            log.info("getTotalTimePercentile : {}", metrics.getTotalTimePercentile(100));
            /**
             * 详见
             * {@link HystrixCommandMetrics.HealthCounts#plus}
             * {@link HystrixCommandMetrics.HealthCounts#toString()}
             */
            log.info("getHealthCounts : {}", metrics.getHealthCounts().toString());
            log.info("circuitBreakerEnabled : {}", metrics.getProperties().circuitBreakerEnabled().get());
        }
    }

    /**
     * localhost:8080/metrics/pool
     */
    @GetMapping("/metrics/pool")
    public void metricsPool() {
        log.info("----------------------------       metricsPool       ----------------------------------");
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
