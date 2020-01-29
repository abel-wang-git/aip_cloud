package com.wanghuiwen.blog.elastic;

import com.wanghuiwen.blog.model.ArticleElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleElasticMapper extends ElasticsearchRepository<ArticleElastic, String> {
}
