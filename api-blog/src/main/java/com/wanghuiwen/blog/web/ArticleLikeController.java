package com.wanghuiwen.blog.web;

import com.wanghuiwen.blog.config.BlogResultEnum;
import com.wanghuiwen.core.annotation.PowerEnable;
import com.wanghuiwen.core.controller.Ctrl;
import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultGenerator;
import com.wanghuiwen.blog.model.ArticleLike;
import com.wanghuiwen.blog.service.ArticleLikeService;
import io.swagger.annotations.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;



/**
* Created by CodeGenerator on 2019/10/30.
*/
@PowerEnable(name = "文章点赞",url = "/article/like")
@Api(value = "文章点赞", tags = {"文章点赞"})
@RestController
@RequestMapping("/article/like")
public class ArticleLikeController extends Ctrl {
    @Resource
    private ArticleLikeService articleLikeService;

    @ApiOperation(value = "文章点赞添加", tags = {"文章点赞"}, notes = "文章点赞添加")
    @PostMapping(value="/add",name="文章点赞添加")
    public Result add(@ApiParam Long articleId, Authentication authentication) {
        try {

        if(authentication==null) return  ResultGenerator.genResult(BlogResultEnum.LOGIN_FIRST);
        ArticleLike articleLike = new ArticleLike();
        articleLike.setArticleId(articleId);
        articleLike.setUid(getAuthUser(authentication).getId());
        articleLike.setCreateTime(new Date());
        articleLikeService.save(articleLike);
        }catch (DuplicateKeyException e){
            return ResultGenerator.genSuccessResult(BlogResultEnum.LIKE_ING);
        }
        return ResultGenerator.genSuccessResult(BlogResultEnum.LIKE);
    }

    @ApiOperation(value = "文章点赞删除", tags = {"文章点赞"}, notes = "文章点赞删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "文章点赞id", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/delete",name="文章点赞删除")
    public Result delete(@RequestParam Long id) {
        articleLikeService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }
}
