package com.hhx.hystrix.config;

import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置监听
 *
 * @author huhuixin3
 * @date 2019/12/27
 */
@Component
public class ConfigListener {

    private static long serviceModifiedTime;
    private static long hystrixModifiedTime;

    @Scheduled(fixedRate = 3000)
    public void listener() throws IOException {
        log.info("ConfigListener.listener...");
        Thread.currentThread().getContextClassLoader().getResource("config/hystrix.properties");
        ClassPathResource resource = new ClassPathResource("config/hystrix.properties");
        File file = resource.getFile();
        if (file.lastModified() != serviceModifiedTime){
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            hystrixChange(properties);
            serviceModifiedTime = file.lastModified();
        }

        resource = new ClassPathResource("config/service.properties");
        file = resource.getFile();
        if (file.lastModified() != hystrixModifiedTime){
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            serviceChange(properties);
            hystrixModifiedTime = file.lastModified();
        }
    }


    private static final Logger log = LoggerFactory.getLogger(ConfigListener.class);

    public synchronized void serviceChange(Properties properties) {
        log.info("ConfigListener.serviceChange {}", properties.toString());
        DynamicServiceProp.serviceErrorPre = Integer.valueOf(properties.getProperty("serviceErrorPre"));
        DynamicServiceProp.serviceTime = Integer.valueOf(properties.getProperty("serviceTime"));
    }


    public synchronized void hystrixChange(Properties properties) {
        log.info("ConfigListener.hystrixChange {}", properties.toString());
        DynamicHystrixProp.groupKey = properties.getProperty("groupKey");
        DynamicHystrixProp.commandKey = properties.getProperty("commandKey");
        DynamicHystrixProp.threadPoolKey = properties.getProperty("threadPoolKey");

        DynamicHystrixProp.commandSetter = HystrixCommandProperties
                .Setter()
                .withCircuitBreakerEnabled(Boolean.valueOf(properties.getProperty("commandCircuitBreakerEnabled")))
                .withCircuitBreakerRequestVolumeThreshold(Integer.valueOf(properties.getProperty("commandCircuitBreakerRequestVolumeThreshold")))
                .withCircuitBreakerSleepWindowInMilliseconds(Integer.valueOf(properties.getProperty("commandCircuitBreakerSleepWindowInMilliseconds")))
                .withCircuitBreakerErrorThresholdPercentage(Integer.valueOf(properties.getProperty("commandCircuitBreakerErrorThresholdPercentage")))
                .withCircuitBreakerForceOpen(Boolean.valueOf(properties.getProperty("commandCircuitBreakerForceOpen")))
                .withCircuitBreakerForceClosed(Boolean.valueOf(properties.getProperty("commandCircuitBreakerForceClosed")))
                .withExecutionIsolationStrategy((HystrixCommandProperties
                        .ExecutionIsolationStrategy.valueOf(properties.getProperty("commandExecutionIsolationStrategy"))))
                .withExecutionIsolationThreadTimeoutInMilliseconds(Integer.valueOf(properties.getProperty("commandExecutionIsolationThreadTimeoutInMilliseconds")))
                .withExecutionTimeoutEnabled(Boolean.valueOf(properties.getProperty("commandExecutionTimeoutEnabled")))
                .withExecutionIsolationThreadInterruptOnTimeout(Boolean.valueOf(properties.getProperty("commandExecutionIsolationThreadInterruptOnTimeout")))
                .withExecutionIsolationThreadInterruptOnFutureCancel(Boolean.valueOf(properties.getProperty("commandExecutionIsolationThreadInterruptOnFutureCancel")))
                .withExecutionIsolationSemaphoreMaxConcurrentRequests(Integer.valueOf(properties.getProperty("commandExecutionIsolationSemaphoreMaxConcurrentRequests")))
                .withFallbackIsolationSemaphoreMaxConcurrentRequests(Integer.valueOf(properties.getProperty("commandFallbackIsolationSemaphoreMaxConcurrentRequests")))
                .withFallbackEnabled(Boolean.valueOf(properties.getProperty("commandFallbackEnabled")))
                .withMetricsRollingStatisticalWindowInMilliseconds(Integer.valueOf(properties.getProperty("commandMetricsRollingStatisticalWindowInMilliseconds")))
                .withMetricsRollingStatisticalWindowBuckets(Integer.valueOf(properties.getProperty("commandMetricsRollingStatisticalWindowBuckets")))
                .withMetricsRollingPercentileEnabled(Boolean.valueOf(properties.getProperty("commandMetricsRollingPercentileEnabled")))
                .withMetricsRollingPercentileWindowInMilliseconds(Integer.valueOf(properties.getProperty("commandMetricsRollingPercentileWindowInMilliseconds")))
                .withMetricsRollingPercentileWindowBuckets(Integer.valueOf(properties.getProperty("commandMetricsRollingPercentileWindowBuckets")))
                .withMetricsRollingPercentileBucketSize(Integer.valueOf(properties.getProperty("commandMetricsRollingPercentileBucketSize")))
                .withMetricsRollingPercentileBucketSize(Integer.valueOf(properties.getProperty("commandMetricsRollingPercentileBucketSize")))
                .withMetricsHealthSnapshotIntervalInMilliseconds(Integer.valueOf(properties.getProperty("commandMetricsHealthSnapshotIntervalInMilliseconds")))
                .withRequestCacheEnabled(Boolean.valueOf(properties.getProperty("commandRequestCacheEnabled")))
                .withRequestLogEnabled(Boolean.valueOf(properties.getProperty("commandRequestLogEnabled")))
        ;

        DynamicHystrixProp.threadPoolSetter = HystrixThreadPoolProperties
                .Setter()
                .withCoreSize(Integer.valueOf(properties.getProperty("threadPoolCoreSize")))
                .withMaximumSize(Integer.valueOf(properties.getProperty("threadPoolMaximumSize")))
                .withMaxQueueSize(Integer.valueOf(properties.getProperty("threadPoolMaxQueueSize")))
                .withKeepAliveTimeMinutes(Integer.valueOf(properties.getProperty("threadPoolKeepAliveTimeMinutes")))
                .withQueueSizeRejectionThreshold(Integer.valueOf(properties.getProperty("threadPoolQueueSizeRejectionThreshold")))
                .withAllowMaximumSizeToDivergeFromCoreSize(Boolean.valueOf(properties.getProperty("threadPoolAllowMaximumSizeToDivergeFromCoreSize")))
                .withMetricsRollingStatisticalWindowBuckets(Integer.valueOf(properties.getProperty("threadPoolMetricsRollingStatisticalWindowBuckets")))
                .withMetricsRollingStatisticalWindowInMilliseconds(Integer.valueOf(properties.getProperty("threadPoolMetricsRollingStatisticalWindowInMilliseconds")))
        ;
    }


}
