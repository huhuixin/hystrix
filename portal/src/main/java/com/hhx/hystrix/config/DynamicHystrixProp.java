package com.hhx.hystrix.config;


import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * @author huhuixin3
 * @date 2019/12/27
 */
public class DynamicHystrixProp {
    public static String groupKey;
    public static String commandKey;
    public static String threadPoolKey;
    public static HystrixCommandProperties.Setter commandSetter;
    public static HystrixThreadPoolProperties.Setter threadPoolSetter;
}
