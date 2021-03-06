package com.hhx.hystrix.controller;

import com.hhx.hystrix.dto.ProductDto;
import com.hhx.hystrix.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author hhx
 */
@Slf4j
@RestController
public class ProductController {

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable("id") Integer id){
        log.info("ProductController.getById: {}", id);
        // id 指定相应时间
        ThreadUtils.sleep(id);
        // 模拟可用率
        if (RandomUtils.nextInt(0, 100) >= 95) {
            log.warn("error ... ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(new ProductDto()
                .setId(id)
                .setName("product" + id)
                .setPrice(BigDecimal.valueOf(id)));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Integer id){
        log.info("ProductController.deleteById: {}", id);
        // id 指定相应时间
        ThreadUtils.sleep(id);
        return ResponseEntity.ok("success");
    }
}
