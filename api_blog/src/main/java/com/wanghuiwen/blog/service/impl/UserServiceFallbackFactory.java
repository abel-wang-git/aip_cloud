package com.wanghuiwen.blog.service.impl;

import com.wanghuiwen.blog.service.UserService;
import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultGenerator;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable throwable) {
        return new UserService() {
            @Override
            public Result findById(Long id) {
                return ResultGenerator.genFailResult();
            }
        };
    }
}
