package com.hhx.hystrix.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hhx.hystrix.config.DynamicHystrixProp;
import com.netflix.hystrix.*;

import static com.hhx.hystrix.config.DynamicServiceProp.serviceErrorPre;
import static com.hhx.hystrix.config.DynamicServiceProp.serviceTime;

/**
 * 动态调整参数
 *
 * @author huhuixin3
 * @date 2019/12/27
 */
public class DynamicCommand extends HystrixCommand<String> {

    private DynamicService dynamicService;

    public DynamicCommand(DynamicService dynamicService) {
        super(dynamicSetter());
        this.dynamicService = dynamicService;
    }

    @Override
    protected String run() {
        return dynamicService.dynamic(serviceTime, serviceErrorPre);
    }

    @Override
    protected String getFallback() {
        return "fallback";
    }

    /**
     * 动态配置
     */
    private static Setter dynamicSetter() {
        return Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(DynamicHystrixProp.groupKey))
                .andCommandKey(HystrixCommandKey.Factory.asKey(DynamicHystrixProp.commandKey))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(DynamicHystrixProp.threadPoolKey))
                .andCommandPropertiesDefaults(DynamicHystrixProp.commandSetter)
                .andThreadPoolPropertiesDefaults(DynamicHystrixProp.threadPoolSetter);
    }

    public static void main(String[] args) throws JsonProcessingException {
        DynamicCommand dynamicCommand = new DynamicCommand(null);
    }
}
