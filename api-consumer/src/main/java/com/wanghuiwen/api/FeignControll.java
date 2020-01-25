package com.wanghuiwen.api;


import com.wanghuiwen.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class FeignControll {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private TestService testService;

    @GetMapping("/feign/dc")
    public String dc() {
        return testService.get();
    }

    @GetMapping("/registry")
    public String registry() {
        logger.info("registry=======================");
        return testService.get();
    }

    @GetMapping("/user/get")
    public Object getCurrentUser(Authentication authentication) {
        logger.info("getCurrentUser=======================");
        return authentication;
    }
}
