package com.hhx.hystrix.service.command;

import com.hhx.hystrix.service.ProductService;
import com.netflix.hystrix.*;

/**
 * @author hhx
 */
public class GetByIdCommand3 extends BaseGetByIdCommand {

    /**
     * 线程池配置
     * 默认配置详见 {@link HystrixThreadPoolProperties}
     */
    private static Setter SETTER = Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("command3.group"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("command3.command"))
            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("command3.pool"))
            .andThreadPoolPropertiesDefaults(
                    HystrixThreadPoolProperties.Setter()
                            // 核心线程数 default_coreSize = 10;
                            .withCoreSize(1)
                            // 最大线程数 default_maximumSize = 10;
                            .withMaximumSize(3)
                            // 最大空闲时间 default_keepAliveTimeMinutes = 1;
                            .withKeepAliveTimeMinutes(1)
                            // 队列长度 default_maxQueueSize = -1;
                            .withMaxQueueSize(100)
                            // default_queueSizeRejectionThreshold = 5;
                            .withQueueSizeRejectionThreshold(1)
                            // default_threadPoolRollingNumberStatisticalWindowBuckets = 10;
                            .withMetricsRollingStatisticalWindowBuckets(10)
                            // default_allow_maximum_size_to_diverge_from_core_size = false;
                            .withAllowMaximumSizeToDivergeFromCoreSize(false)
                            // default_threadPoolRollingNumberStatisticalWindow = 10000;
                            .withMetricsRollingStatisticalWindowInMilliseconds(1000));

    public GetByIdCommand3(ProductService productService, Integer id) {
        super(SETTER, productService, id);
    }
}
