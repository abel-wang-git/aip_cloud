package com.wanghuiwen.blog.service;
import com.wanghuiwen.blog.model.ArticleElastic;


public interface ElasticsearchService {
    Iterable<ArticleElastic> findAll();

    ArticleElastic add();
}
