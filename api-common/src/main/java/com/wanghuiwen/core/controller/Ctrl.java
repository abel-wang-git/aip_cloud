package com.wanghuiwen.core.controller;

import com.wanghuiwen.core.ServiceException;
import com.wanghuiwen.core.model.AuthUser;
import org.springframework.security.core.Authentication;

/**
 * control 基类
 */
public abstract class Ctrl {

    protected AuthUser getAuthUser(Authentication authentication){
        if(authentication == null) throw  new ServiceException("当前用户未登录");
        return (AuthUser) authentication.getPrincipal();
    }
}
