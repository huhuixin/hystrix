package com.hhx.hystrix.service.command;

import com.hhx.hystrix.service.ProductService;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author hhx
 */
public class GetByIdCommand1 extends BaseGetByIdCommand {

    /**
     * 只设置一个简单的名字
     * commandKey 默认类名  threadPoolKey 默认与groupKey相同
     */
    private static Setter SETTER = Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("command1.group"));

    public GetByIdCommand1(ProductService productService, Integer id) {
        super(SETTER, productService, id);
    }
}
