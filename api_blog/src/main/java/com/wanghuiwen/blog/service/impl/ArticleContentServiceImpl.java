package com.wanghuiwen.blog.service.impl;

import com.wanghuiwen.blog.dao.ArticleContentMapper;
import com.wanghuiwen.blog.dao.ArticleLikeMapper;
import com.wanghuiwen.blog.dao.ArticleListMapper;
import com.wanghuiwen.blog.model.ArticleContent;
import com.wanghuiwen.blog.model.ArticleLike;
import com.wanghuiwen.blog.model.ArticleList;
import com.wanghuiwen.blog.service.ArticleContentService;
import com.wanghuiwen.blog.service.UserService;
import com.wanghuiwen.core.config.authserver.AuthUser;
import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultGenerator;
import com.wanghuiwen.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2019/10/30.
 */
@Service
@Transactional
public class ArticleContentServiceImpl extends AbstractService<ArticleContent> implements ArticleContentService {
    @Resource
    private ArticleContentMapper articleContentMapper;
    @Resource
    private ArticleLikeMapper articleLikeMapper;

    @Resource
    private ArticleListMapper articleListMapper;
    @Resource
    private UserService userService;
    @Override
    public Result detail(Integer id, AuthUser authUser) {
        ArticleList articleList = articleListMapper.selectByPrimaryKey(id);
        articleList.setHot(articleList.getHot()==null?1:articleList.getHot()+1);
        articleListMapper.updateByPrimaryKeySelective(articleList);

        ArticleContent content = articleContentMapper.selectByPrimaryKey(id);
        Result result = userService.findById(3L);
//        User user = (User) result.getData();

        Map<String , Object> res = new HashMap<>();
        if(authUser!=null){
            System.out.println(authUser.getId());
            List<ArticleLike> like= articleLikeMapper.selectByUid(authUser.getId(),id);
            res.put("isLike", like.size() > 0);
        }else {
            res.put("isLike", false);
        }
        res.put("list",articleList);
        res.put("content",content);
        res.put("author",result.getData());
        return ResultGenerator.genSuccessResult(res);
    }
}
