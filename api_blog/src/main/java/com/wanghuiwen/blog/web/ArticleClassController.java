package com.wanghuiwen.blog.web;

import com.wanghuiwen.core.annotation.PowerEnable;
import com.wanghuiwen.core.config.authserver.AuthUser;
import com.wanghuiwen.core.controller.Ctrl;
import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultGenerator;
import com.wanghuiwen.blog.model.ArticleClass;
import com.wanghuiwen.blog.service.ArticleClassService;
import io.swagger.annotations.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;



/**
* Created by CodeGenerator on 2019/10/30.
*/
@PowerEnable(name = "文章类型",url = "/article/class")
@Api(value = "文章类型", tags = {"文章类型"})
@RestController
@RequestMapping("/article/class")
public class ArticleClassController extends Ctrl {
    @Resource
    private ArticleClassService articleClassService;

    @ApiOperation(value = "文章类型添加", tags = {"文章类型"}, notes = "文章类型添加")
    @PostMapping(value="/add",name="文章类型添加")
    public Result add(@ApiParam ArticleClass articleClass,
                      Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        articleClass.setUid(authUser.getId());
        articleClassService.save(articleClass);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "文章类型删除", tags = {"文章类型"}, notes = "文章类型删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "文章类型id", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/delete",name="文章类型删除")
    public Result delete(@RequestParam Long id) {
        articleClassService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "文章类型修改", tags = {"文章类型"}, notes = "文章类型修改,对象主键必填")
    @PostMapping(value="/update",name="文章类型修改")
    public Result update(@ApiParam ArticleClass articleClass) {
        articleClassService.update(articleClass);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "文章类型列表信息", tags = {"文章类型"}, notes = "文章类型列表信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "where", value = "条件构建", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "size", value = "每页显示的条数", dataType = "String", paramType = "query",defaultValue="10")
    })
    @PostMapping(value="/list",name="文章类型列表信息")
    public Result list(Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        Condition condition = new Condition(ArticleClass.class);
        condition.createCriteria().andEqualTo("uid",authUser.getId());

        List<ArticleClass> list = articleClassService.findByCondition(condition);
        return ResultGenerator.genSuccessResult(list);
    }
}
