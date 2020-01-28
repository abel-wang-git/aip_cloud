package com.wanghuiwen.blog.model.constant;

public enum ArticleStatus {
    NORM((byte) 1,"发布"),
    PRIVATE((byte) 2,"不公开"),
    BAN((byte) 3,"屏蔽"),
    ;
    private byte status;
    private String statuName;

    ArticleStatus(byte status, String statuName) {
        this.status= status;
        this.statuName= statuName;
    }

    public byte getStatus() {
        return status;
    }
}
