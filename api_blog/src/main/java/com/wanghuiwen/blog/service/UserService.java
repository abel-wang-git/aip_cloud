package com.wanghuiwen.blog.service;

import com.wanghuiwen.blog.config.FeignConfiguration;
import com.wanghuiwen.blog.service.impl.UserServiceFallbackFactory;
import com.wanghuiwen.core.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "auth",fallbackFactory= UserServiceFallbackFactory.class,configuration = FeignConfiguration.class)
public interface UserService {
    @RequestMapping(value = "/user/detail",method = RequestMethod.POST)
    Result findById(@RequestParam Long id);
}
