package com.hhx.hystrix.service.command;

import com.hhx.hystrix.dto.ProductDto;
import com.hhx.hystrix.service.ProductService;
import com.netflix.hystrix.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author hhx
 */
@Slf4j
public class BaseGetByIdCommand extends HystrixCommand<ProductDto> {

    public static List<Class<? extends BaseGetByIdCommand>> COMMAND_CLASSES =
            Arrays.asList(GetByIdCommand1.class, GetByIdCommand2.class);

    public static Class<? extends BaseGetByIdCommand> randomCommandClass(){
        return COMMAND_CLASSES.get(RandomUtils.nextInt(COMMAND_CLASSES.size()));
    }

    private ProductService productService;
    private Integer id;


    public BaseGetByIdCommand(Setter setter, ProductService productService, Integer id) {
        super(setter);
        this.productService = productService;
        this.id = id;
    }

    @Override
    protected ProductDto run() {
        log.info("{}.run id: {}", getClass().getSimpleName(), id);
        return productService.getById(id);
    }

    @Override
    protected ProductDto getFallback() {
        log.info("{}.getFallback id: {}", getClass().getSimpleName(), id);
        return null;
    }
}
