package com.wanghuiwen.blog.service.impl;

import com.wanghuiwen.blog.dao.ArticleClassMapper;
import com.wanghuiwen.blog.model.ArticleClass;
import com.wanghuiwen.blog.service.ArticleClassService;
import com.wanghuiwen.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/10/30.
 */
@Service
@Transactional
public class ArticleClassServiceImpl extends AbstractService<ArticleClass> implements ArticleClassService {
    @Resource
    private ArticleClassMapper articleClassMapper;

}
