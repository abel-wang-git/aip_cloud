package com.wanghuiwen.blog.dao;
import com.wanghuiwen.blog.model.ArticleComment;
import com.wanghuiwen.blog.model.vo.ArticleCommentPojo;
import com.wanghuiwen.blog.model.vo.ArticleCommentReplyPojo;
import com.wanghuiwen.core.Mapper;

import java.util.List;

public interface ArticleCommentMapper extends Mapper<ArticleComment> {
    List<ArticleCommentPojo> selectComment(Long articleId);

    List<ArticleCommentReplyPojo> selectReComment(Long articleId);
}