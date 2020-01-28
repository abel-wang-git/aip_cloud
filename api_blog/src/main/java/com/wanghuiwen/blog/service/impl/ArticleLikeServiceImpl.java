package com.wanghuiwen.blog.service.impl;

import com.wanghuiwen.core.service.AbstractService;
import com.wanghuiwen.blog.dao.ArticleLikeMapper;
import com.wanghuiwen.blog.model.ArticleLike;
import com.wanghuiwen.blog.service.ArticleLikeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/10/30.
 */
@Service
@Transactional
public class ArticleLikeServiceImpl extends AbstractService<ArticleLike> implements ArticleLikeService {
    @Resource
    private ArticleLikeMapper articleLikeMapper;

}
