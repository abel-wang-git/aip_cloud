package com.wanghuiwen.auth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collection;
import java.util.List;

public class AuthMetadataSource implements FilterInvocationSecurityMetadataSource {
    Logger logger =  LoggerFactory.getLogger(this.getClass());

    /**
     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法。
     * object-->FilterInvocation
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;

        if (isMatcherAllowedRequest(filterInvocation)) return null ; //return null 表示允许访问，不做拦截
        //URL规则匹配.
        //没有有匹配到，需要指定相应的角色：
        return org.springframework.security.access.SecurityConfig.createList(filterInvocation.getRequestUrl());
    }




    /**
     * 判断当前请求是否在允许请求的范围内
     * @param fi 当前请求
     * @return 是否在范围中
     */
    private boolean isMatcherAllowedRequest(FilterInvocation fi){
        return false;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
