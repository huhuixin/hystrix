package com.hhx.hystrix.controller;

import com.hhx.hystrix.dto.ProductDto;
import com.hhx.hystrix.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hhx
 */
@Slf4j
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/{id}")
    public ProductDto getById(@PathVariable("id") Integer id){
        log.info("ProductController.getById: {} ------ start", id);
        ProductDto result = productService.getById(id);
        log.info("ProductController.getById: {} ------ end", id);
        return result;
    }

    @DeleteMapping("/product/{id}")
    public String deleteById(@PathVariable("id") Integer id){
        log.info("ProductController.deleteById: {}", id);
        productService.deleteById(id);
        return "success";
    }
}
