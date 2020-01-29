package com.wanghuiwen.blog.dao;


import com.wanghuiwen.blog.model.ArticleList;
import com.wanghuiwen.core.Mapper;

import java.util.List;
import java.util.Map;

public interface ArticleListMapper extends Mapper<ArticleList> {
    List<Map<String, Object>> selectIndex(Map<String, Object> params);
}