package com.hhx.hystrix.service;

import com.hhx.hystrix.dto.ProductDto;
import com.hhx.hystrix.service.command.BaseGetByIdCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Future;

/**
 * @author hhx
 */
@Slf4j
@Service
public class ProductHystrixService {

    @Autowired
    private ProductService productService;

    public ProductDto getById(Class<? extends BaseGetByIdCommand> clazz, Integer id) {
        return getCommandInstance(clazz, id).execute();
    }

    public Future<ProductDto> getByIdAsync(Class<? extends BaseGetByIdCommand> clazz, Integer id) {
        return getCommandInstance(clazz, id).queue();
    }

    private <T extends BaseGetByIdCommand> T getCommandInstance(Class<T> clazz, Integer id){
        try {
            return clazz.getConstructor(ProductService.class, Integer.class).newInstance(productService, id);
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            log.error("class {} 找不到构造器!", clazz.getSimpleName(), e);
            throw new RuntimeException(e.getMessage());
        }
    }


    @com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand(
            fallbackMethod = "onDeleteByIdError",
            groupKey = "product.getById")
    public Boolean deleteById(Integer id) {
        return productService.deleteById(id);
    }

    public Boolean onDeleteByIdError(Integer id) {
        log.info("GetByIdCommand.getFallback id: {}", id);
        return Boolean.FALSE;
    }
}
