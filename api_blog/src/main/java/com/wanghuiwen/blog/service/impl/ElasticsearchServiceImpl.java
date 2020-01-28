package com.wanghuiwen.blog.service.impl;

import com.wanghuiwen.blog.elastic.ArticleElasticMapper;
import com.wanghuiwen.blog.model.ArticleElastic;
import com.wanghuiwen.blog.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Autowired
    private ArticleElasticMapper elasticArticle;

    @Override
    public Iterable<ArticleElastic> findAll() {
        return elasticArticle.findAll();
    }

    @Override
    public ArticleElastic add() {

        return elasticArticle.save(new ArticleElastic());
    }
}
