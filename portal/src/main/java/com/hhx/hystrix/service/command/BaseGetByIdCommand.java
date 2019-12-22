package com.hhx.hystrix.service.command;

import com.hhx.hystrix.dto.ProductDto;
import com.hhx.hystrix.service.ProductService;
import com.netflix.hystrix.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author hhx
 */
@Slf4j
public class BaseGetByIdCommand extends HystrixCommand<ProductDto> {

    private static List<Class<? extends BaseGetByIdCommand>> ALL =
            Arrays.asList(GetByIdCommand1.class, GetByIdCommand2.class, GetByIdCommand3.class);

    public static Class<? extends BaseGetByIdCommand> randomCommandClass(){
        return ALL.get(RandomUtils.nextInt(0, ALL.size()));
    }

    private ProductService productService;
    private Integer id;


    BaseGetByIdCommand(Setter setter, ProductService productService, Integer id) {
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
