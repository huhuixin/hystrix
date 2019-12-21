package com.hhx.hystrix.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author hhx
 */
@Data
@Accessors(chain = true)
public class ProductDto {
    private Integer id;
    private String name;
    private BigDecimal price;
}
