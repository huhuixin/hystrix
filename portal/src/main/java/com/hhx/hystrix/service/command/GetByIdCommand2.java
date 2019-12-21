package com.hhx.hystrix.service.command;

import com.hhx.hystrix.service.ProductService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * @author hhx
 */
public class GetByIdCommand2 extends BaseGetByIdCommand {

    /**
     * group command & threadPool key
     */
    private static Setter SETTER = Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("command2.group"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("command2.command"))
            // threadPoolKey 相同会使用同一个线程池
            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("command2.pool"))
            ;

    public GetByIdCommand2(ProductService productService, Integer id) {
        super(SETTER, productService, id);
    }
}
