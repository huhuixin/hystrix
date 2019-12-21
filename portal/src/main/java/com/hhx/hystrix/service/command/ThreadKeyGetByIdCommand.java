package com.hhx.hystrix.service.command;

import com.hhx.hystrix.service.ProductService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * @author hhx
 */
public class ThreadKeyGetByIdCommand extends BaseGetByIdCommand {

    private static HystrixCommandGroupKey GROUP_KEY = HystrixCommandGroupKey.Factory
            .asKey("product.getById.thread.group");

    private static HystrixThreadPoolKey THREAD_POOL_KEY = HystrixThreadPoolKey.Factory
            .asKey("product.getById.thread.pool");

    public ThreadKeyGetByIdCommand(ProductService productService, Integer id) {
        super(GROUP_KEY, THREAD_POOL_KEY, productService, id);
    }
}
