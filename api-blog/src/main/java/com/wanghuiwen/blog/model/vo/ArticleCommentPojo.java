package com.wanghuiwen.blog.model.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleCommentPojo {
    private Long commentId;
    private Date createTime;
    private String comment;
    private String name;
    private String avatar;
    private List<ArticleCommentReplyPojo> reply;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<ArticleCommentReplyPojo> getReply() {
        if (this.reply==null){
            this.reply=new ArrayList<>();
        }
        return reply;
    }

    public void setReply(List<ArticleCommentReplyPojo> reply) {
        this.reply = reply;
    }
}
