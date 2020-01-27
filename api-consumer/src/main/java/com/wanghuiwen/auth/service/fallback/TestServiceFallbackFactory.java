package com.wanghuiwen.auth.service.fallback;

import com.wanghuiwen.auth.service.TestService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class TestServiceFallbackFactory implements FallbackFactory<TestService> {
    @Override
    public TestService create(Throwable throwable) {
        return new TestService() {
            @Override
            public String get() {
                return "服务降级";
            }
        };
    }
}
