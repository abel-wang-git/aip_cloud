package com.wanghuiwen.blog.web;

import com.wanghuiwen.blog.config.BlogResultEnum;
import com.wanghuiwen.core.annotation.PowerEnable;
import com.wanghuiwen.core.controller.Ctrl;
import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultGenerator;
import com.wanghuiwen.core.response.ResultMessage;
import com.wanghuiwen.blog.model.ArticleComment;
import com.wanghuiwen.blog.service.ArticleCommentService;
import io.swagger.annotations.*;
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
@PowerEnable(name = "文章评论",url = "/article/comment")
@Api(value = "文章评论", tags = {"文章评论"})
@RestController
@RequestMapping("/article/comment")
public class ArticleCommentController extends Ctrl {
    @Resource
    private ArticleCommentService articleCommentService;

    @ApiOperation(value = "文章评论添加", tags = {"文章评论"}, notes = "文章评论添加")
    @PostMapping(value="/add",name="文章评论添加")
    public Result add(@ApiParam ArticleComment articleComment,
                      Authentication authentication) {
        if(authentication!=null){
            articleComment.setUid(getAuthUser(authentication).getId());
        }else {
            return  ResultGenerator.genResult(BlogResultEnum.LOGIN_FIRST);
        }

        articleComment.setCreateTime(new Date());
        articleCommentService.save(articleComment);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "文章评论删除", tags = {"文章评论"}, notes = "文章评论删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "文章评论id", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/delete",name="文章评论删除")
    public Result delete(@RequestParam Long id) {
        articleCommentService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "文章评论修改", tags = {"文章评论"}, notes = "文章评论修改,对象主键必填")
    @PostMapping(value="/update",name="文章评论修改")
    public Result update(@ApiParam ArticleComment articleComment) {
        articleCommentService.update(articleComment);
        return ResultGenerator.genSuccessResult();
    }


    @ApiOperation(value = "文章评论列表信息", tags = {"文章评论"}, notes = "文章评论列表信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "articleId", value = "文章id", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/list",name="文章评论列表信息")
    public Result list(Long articleId) {
        return articleCommentService.byArticle(articleId);
    }
}
