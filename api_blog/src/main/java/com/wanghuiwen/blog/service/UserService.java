package com.wanghuiwen.blog.service;

import com.wanghuiwen.blog.service.impl.UserServiceFallbackFactory;
import com.wanghuiwen.core.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "auth",fallbackFactory= UserServiceFallbackFactory.class)
public interface UserService {
    @RequestMapping(value = "/dc",method = RequestMethod.GET)
    Result findById(Long id);
}
