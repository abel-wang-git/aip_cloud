package com.wanghuiwen.blog.config;


import com.wanghuiwen.core.response.IResultEnum;

public enum BlogResultEnum implements IResultEnum {
    LOGIN_FIRST("请先登录",200403),
    LIKE_ING("已经点过赞了",200),
    LIKE("点赞成功",200),
    ;

    private int code;
    private String message;

    BlogResultEnum(String message,int code) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
