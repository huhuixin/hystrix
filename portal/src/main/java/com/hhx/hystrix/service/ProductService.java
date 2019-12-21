package com.hhx.hystrix.service;

import com.hhx.hystrix.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author hhx
 */
@Service
public class ProductService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://127.0.0.1:8081";

    public ProductDto getById(Integer id){
        return restTemplate
                .getForEntity(BASE_URL + "/product/" + id, ProductDto.class).getBody();
    }

    public Boolean deleteById(Integer id){
        restTemplate.delete(BASE_URL + "/product/" + id);
        return Boolean.TRUE;
    }
}
