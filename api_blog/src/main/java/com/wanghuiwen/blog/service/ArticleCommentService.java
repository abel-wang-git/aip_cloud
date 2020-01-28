package com.wanghuiwen.blog.service;


import com.wanghuiwen.blog.model.ArticleComment;
import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.service.Service;

/**
 * Created by CodeGenerator on 2019/10/30.
 */
public interface ArticleCommentService extends Service<ArticleComment> {

    Result byArticle(Long articleId);
}
