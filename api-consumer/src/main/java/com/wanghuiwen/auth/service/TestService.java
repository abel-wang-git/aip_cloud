package com.wanghuiwen.auth.service;

import com.wanghuiwen.auth.service.fallback.TestServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "auth",fallbackFactory= TestServiceFallbackFactory.class)
public interface TestService {
    @RequestMapping(value = "/dc",method = RequestMethod.GET)
    String get();
}
