package com.hhx.hystrix.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhx.hystrix.dto.ProductDto;
import com.hhx.hystrix.service.ProductHystrixService;
import com.hhx.hystrix.service.ProductService;
import com.hhx.hystrix.service.command.BaseGetByIdCommand;
import com.hhx.hystrix.service.command.GroupKeyGetByIdCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author hhx
 */
@Slf4j
@RestController
public class ProductHystrixController {

    @Autowired
    private ProductHystrixService productHystrixService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/hystrix/product/{id}/{asyncId}")
    public List<ProductDto> getById(@PathVariable("id") Integer id, @PathVariable("asyncId") Integer asyncId) throws ExecutionException, InterruptedException, JsonProcessingException {
        log.info("productHystrixService.getByIdAsync: {} ------ start", asyncId);
        Future<ProductDto> resultFuture = productHystrixService.getByIdAsync(GroupKeyGetByIdCommand.class, asyncId);
        log.info("productService.getById: {} ------ start", id);
        ProductDto result = productService.getById(id);
        ProductDto resultAsync;
        log.info("productHystrixService.getByIdAsync: {} ------ end", objectMapper.writeValueAsString(result));
        log.info("productService.getById: {} ------ end", objectMapper.writeValueAsString(resultAsync = resultFuture.get()));
        return Arrays.asList(result, resultAsync);
    }


    @GetMapping("/hystrix/product/{ids}")
    public List<ProductDto> getByIds(@PathVariable("ids") String ids) {
        try {
            log.info("productHystrixService.getByIds: {} ------ start", ids);
            List<Future<ProductDto>> futures = Stream.of(ids.split(","))
                    .mapToInt(Integer::valueOf)
                    .mapToObj(id -> productHystrixService.getByIdAsync(BaseGetByIdCommand.randomCommandClass(), id)
                    ).collect(Collectors.toList());
            return futures.stream().map(f -> {
                try {
                    return f.get();
                } catch (InterruptedException | ExecutionException e) {
                    log.error("ProductHystrixController.getByIds ids: {}", ids, e);
                    return null;
                }
            }).collect(Collectors.toList());
        } finally {
            log.info("productHystrixService.getByIds: {} ------ end", ids);
        }
    }
}