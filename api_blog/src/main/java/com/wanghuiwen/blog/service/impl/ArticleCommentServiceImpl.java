package com.wanghuiwen.blog.service.impl;

import com.wanghuiwen.blog.dao.ArticleCommentMapper;
import com.wanghuiwen.blog.model.ArticleComment;
import com.wanghuiwen.blog.model.vo.ArticleCommentPojo;
import com.wanghuiwen.blog.model.vo.ArticleCommentReplyPojo;
import com.wanghuiwen.blog.service.ArticleCommentService;
import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultGenerator;
import com.wanghuiwen.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/10/30.
 */
@Service
@Transactional
public class ArticleCommentServiceImpl extends AbstractService<ArticleComment> implements ArticleCommentService {
    @Resource
    private ArticleCommentMapper articleCommentMapper;

    @Override
    public Result byArticle(Long articleId) {

        //获取文章的评论
        List<ArticleCommentPojo> articleComments = articleCommentMapper.selectComment(articleId);

        //获取评论的回复
        List<ArticleCommentReplyPojo> reComments = articleCommentMapper.selectReComment(articleId);

        for (ArticleCommentPojo comment : articleComments) {
            List<ArticleCommentReplyPojo> replyPojos = setReply(reComments, comment.getCommentId(), comment.getName());
            comment.getReply().addAll(replyPojos);
            while (replyPojos.size() > 0) {
                for (ArticleCommentReplyPojo reply : replyPojos) {
                    replyPojos = setReply(reComments, reply.getCommentId(), reply.getName());
                    comment.getReply().addAll(replyPojos);
                }
            }
        }
        return ResultGenerator.genSuccessResult(articleComments);
    }

    /**
     * 同一评论的回复放到这个评论的reply里
     *
     * @param reComments
     * @param pid
     */
    private List<ArticleCommentReplyPojo> setReply(List<ArticleCommentReplyPojo> reComments, Long pid, String name) {
        List<ArticleCommentReplyPojo> res = new ArrayList<>();
        Iterator<ArticleCommentReplyPojo> iterable = reComments.iterator();
        while (iterable.hasNext()) {
            ArticleCommentReplyPojo replyPojo = iterable.next();
            if (replyPojo.getPid().equals(pid)) {
                replyPojo.setReplyName(name);
                res.add(replyPojo);
                iterable.remove();
            }
        }
        return res;
    }
}
