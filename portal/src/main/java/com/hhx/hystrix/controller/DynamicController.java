package com.hhx.hystrix.controller;

import com.hhx.hystrix.service.DynamicCommand;
import com.hhx.hystrix.service.DynamicService;
import com.netflix.hystrix.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author hhx
 */
@Slf4j
@RestController
public class DynamicController {

    @Autowired
    private DynamicService service;

    /**
     * 批量执行
     * @param times 执行次数
     */
    @GetMapping("/dynamic/{times}")
    public List<String> dynamic(@PathVariable Integer times){
        log.info("DynamicController.dynamic: times {}", times);
        List<DynamicCommand> commands = new ArrayList<>(times);
        for (Integer i = 0; i < times; i++) {
            commands.add(new DynamicCommand(service));
        }
        return commands.stream()
                .map(HystrixCommand::queue)
                .map(f -> {
                        try {
                            return f.get();
                        } catch (InterruptedException | ExecutionException e) {
                            log.error("DynamicController.dynamic exception", e);
                            return "exception";
                        }
                    })
                .collect(Collectors.toList());
    }

}
