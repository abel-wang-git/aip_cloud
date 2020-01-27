package com.wanghuiwen.core.annotation;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class HttpsCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return "true".equalsIgnoreCase(conditionContext.getEnvironment().getProperty("server.ssl.enabled"));
    }
}
