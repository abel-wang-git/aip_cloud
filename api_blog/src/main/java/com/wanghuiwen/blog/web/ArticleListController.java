package com.wanghuiwen.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanghuiwen.blog.model.ArticleElastic;
import com.wanghuiwen.blog.model.ArticleList;
import com.wanghuiwen.blog.service.ArticleContentService;
import com.wanghuiwen.blog.service.ArticleListService;
import com.wanghuiwen.blog.service.ElasticsearchService;
import com.wanghuiwen.core.annotation.PowerEnable;
import com.wanghuiwen.core.controller.Ctrl;
import com.wanghuiwen.core.model.AuthUser;
import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/10/30.
 */
@PowerEnable(name = "文章列表信息", url = "/article/list")
@Api(value = "文章列表信息", tags = {"文章列表信息"})
@RestController
@RequestMapping("/article/list")
public class ArticleListController extends Ctrl {
    @Resource
    private ArticleListService articleListService;
    @Resource
    private ArticleContentService articleContentService;
    @Resource
    private ElasticsearchService elasticsearchService;

    @ApiOperation(value = "文章列表信息添加", tags = {"文章列表信息"}, notes = "文章列表信息添加")
    @PostMapping(value = "/add", name = "文章信息添加")
    public Result add(Authentication authentication,
                      @RequestParam String title,
                      @RequestParam String content,
                      @RequestParam String coverPicture,
                      @RequestParam Long classId,
                      @RequestParam Byte status,
                      @RequestParam Boolean top) {
        AuthUser authUser = getAuthUser(authentication);



        return articleListService.add(title, content,coverPicture,classId,status,top,authUser.getNickname(),authUser.getId());
    }

    @ApiOperation(value = "文章列表信息删除", tags = {"文章列表信息"}, notes = "文章列表信息删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "文章列表信息id", dataType = "Long", paramType = "query")
    })
    @PostMapping(value = "/delete", name = "文章列表信息删除")
    public Result delete(@RequestParam Long id) {
        articleListService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "文章列表信息修改", tags = {"文章列表信息"}, notes = "文章列表信息修改,对象主键必填")
    @PostMapping(value = "/update", name = "文章列表信息修改")
    public Result update(@RequestParam Long articleId,
                         @RequestParam String title,
                         @RequestParam String content,
                         @RequestParam String coverPicture,
                         @RequestParam Long classId,
                         @RequestParam Byte status,
                         @RequestParam Boolean top) {
        articleListService.update(articleId, title, content, coverPicture, classId, status, top);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "文章列表信息详细信息", tags = {"文章列表信息"}, notes = "文章列表信息详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "文章列表信息id", dataType = "Long", paramType = "query")
    })
    @PostMapping(value = "/detail", name = "文章列表信息详细信息")
    public Result detail(@RequestParam Integer id,
                         Authentication authentication) {
        return articleContentService.detail(id,getAuthUser(authentication));
    }

    @ApiOperation(value = "文章列表信息列表信息", tags = {"文章列表信息"}, notes = "文章列表信息列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数", dataType = "String", paramType = "query", defaultValue = "10")
    })
    @PostMapping(value = "/list", name = "文章列表信息列表信息")
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<ArticleList> list = articleListService.findAll();
        PageInfo<ArticleList> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @ApiOperation(value = "文章首页信息", tags = {"文章列表信息"}, notes = "文章首页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数", dataType = "String", paramType = "query", defaultValue = "10")
    })
    @PostMapping(value = "/index", name = "文章首页")
    public Result index(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {

        return articleListService.selectIndex(page, size);
    }

    @GetMapping(value = "/query", name = "全文搜索")
    public Result query() {
        Iterable<ArticleElastic> articleLists = elasticsearchService.findAll();
        return ResultGenerator.genSuccessResult(articleLists);
    }
}
