package com.wanghuiwen.blog.dao;

import com.wanghuiwen.blog.model.ArticleLike;
import com.wanghuiwen.core.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleLikeMapper extends Mapper<ArticleLike> {
    List<ArticleLike> selectByUid(@Param("userId") Long userId, @Param("id") Integer id);
}