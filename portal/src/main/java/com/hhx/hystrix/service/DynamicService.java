package com.hhx.hystrix.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author hhx
 */
@Service
public class DynamicService {

    private static final Logger log = LoggerFactory.getLogger(DynamicService.class);

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://127.0.0.1:8081";

    public String dynamic(Integer time, Integer errorPer){
        long start = System.currentTimeMillis();
        log.info("DynamicService.dynamic time {} errorPer {}", time, errorPer);
        ResponseEntity<String> entity = restTemplate.getForEntity(BASE_URL + "/dynamic?time={time}&errorPer={errorPer}", String.class, time, errorPer);
        log.info("DynamicService.dynamic status {} body {} time {}", entity.getStatusCodeValue(), entity.getBody(), System.currentTimeMillis() - start);
        return entity.getBody();
    }
}
