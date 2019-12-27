package com.hhx.hystrix.controller;

import com.hhx.hystrix.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hhx
 */
@Slf4j
@RestController
public class DynamicController {

    /**
     * 动态控制错误率和响应时长
     * @param time 响应时长范围
     * @param errorPer 错误率
     */
    @GetMapping("/dynamic")
    public ResponseEntity<String> dynamic(Integer time, Integer errorPer){
        log.info("DynamicController.dynamic: time {} errorPer {}", time, errorPer);
        if (RandomUtils.nextInt(0, 100) < errorPer){
            log.error("error");
            // throw new RuntimeException("error");
            return ResponseEntity.status(500).body("error");
        } else {
            ThreadUtils.sleep(RandomUtils.nextInt(0, time));
        }
        return ResponseEntity.ok("success");
    }

}
