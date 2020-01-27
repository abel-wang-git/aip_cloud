package com.wanghuiwen.auth.dao;

import com.wanghuiwen.auth.model.SysWhitelist;
import com.wanghuiwen.core.Mapper;
import org.apache.ibatis.annotations.Param;

public interface SysWhitelistMapper extends Mapper<SysWhitelist> {
    void update(@Param("url") String url, @Param("id") String id);
}