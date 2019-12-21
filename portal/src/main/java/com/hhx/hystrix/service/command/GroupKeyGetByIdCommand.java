package com.hhx.hystrix.service.command;

import com.hhx.hystrix.service.ProductService;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author hhx
 */
public class GroupKeyGetByIdCommand extends BaseGetByIdCommand {

    private static HystrixCommandGroupKey GROUP_KEY = HystrixCommandGroupKey.Factory
            .asKey("product.getById.group.group");

    public GroupKeyGetByIdCommand(ProductService productService, Integer id) {
        super(GROUP_KEY, productService, id);
    }
}
