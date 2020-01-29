package com.wanghuiwen.blog.service.impl;

import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultGenerator;
import com.wanghuiwen.core.response.ResultMessage;
import com.wanghuiwen.core.service.AbstractService;
import com.wanghuiwen.blog.dao.ArticleContentMapper;
import com.wanghuiwen.blog.elastic.ArticleElasticMapper;
import com.wanghuiwen.blog.dao.ArticleListMapper;
import com.wanghuiwen.blog.model.ArticleContent;
import com.wanghuiwen.blog.model.ArticleElastic;
import com.wanghuiwen.blog.model.ArticleList;
import com.wanghuiwen.blog.service.ArticleListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2019/10/30.
 */
@Service
@Transactional
public class ArticleListServiceImpl extends AbstractService<ArticleList> implements ArticleListService {
    @Resource
    private ArticleListMapper articleListMapper;
    @Resource
    private ArticleContentMapper articleContentMapper;
    @Resource
    private ArticleElasticMapper articleElasticMapper;

    @Override
    public Result update(Long id, String title, String content, String coverPicture, Long classId, Byte status, Boolean top) {
        ArticleList list = findById(id);
        ArticleContent articleContent = articleContentMapper.selectByPrimaryKey(id);

        if (list == null) return ResultGenerator.genFailResult(ResultMessage.NO_CONTENT);

        list.setTitle(title);
        list.setCoverPicture(coverPicture);
        list.setClassId(classId);
        list.setTop(top);
        list.setStatus(status);
        update(list);
        articleContent.setContent(content);
        articleContentMapper.updateByPrimaryKeySelective(articleContent);
        return ResultGenerator.genSuccessResult();
    }

    @Override
    public Result selectIndex(Integer page, Integer size) {
        Map<String, Object> params = new HashMap<>();
        PageHelper.startPage(page, size);
        List<Map<String, Object>> res = articleListMapper.selectIndex(params);
        return ResultGenerator.genSuccessResult(new PageInfo<>(res));
    }

    @Override
    public Result add(String title, String content, String coverPicture, Long classId, Byte status, Boolean top, String nickname, Long uid) {
        ArticleList list = new ArticleList();
        list.setClassId(classId);
        list.setCoverPicture(coverPicture);
        list.setTitle(title);
        list.setUid(uid);
        list.setCreateTime(new Date());
        list.setStatus(status);
        list.setTop(top);
        list.setHot(0);

        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(content);

        ArticleElastic elastic = new ArticleElastic();
        elastic.setCoverPicture(list.getCoverPicture());
        elastic.setCreateTime(list.getCreateTime());
        elastic.setHot(list.getHot());
        elastic.setStatus(list.getStatus());
        elastic.setTitle(list.getTitle());
        elastic.setTop(list.getTop());
        elastic.setContent(content);
        elastic.setAuthor(nickname);

        articleListMapper.insert(list);
        elastic.setArticleId(list.getArticleId());
        articleContent.setArticleId(list.getArticleId());

        articleContentMapper.insert(articleContent);
        articleElasticMapper.save(elastic);
        return ResultGenerator.genSuccessResult();
    }
}
