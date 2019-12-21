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
                            // 核心线程数
                            .withCoreSize(1)
                            // 最大线程数
                            .withMaximumSize(3)
                            // 最大空闲时间
                            .withKeepAliveTimeMinutes(1)
                            // 队列长度
                            .withMaxQueueSize(100)
                            .withQueueSizeRejectionThreshold(1)
                            .withMetricsRollingStatisticalWindowBuckets(1)
                            .withAllowMaximumSizeToDivergeFromCoreSize(true)
                            .withMetricsRollingStatisticalWindowInMilliseconds(1));

    public GetByIdCommand3(ProductService productService, Integer id) {
        super(SETTER, productService, id);
    }
}
