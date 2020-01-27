package com.wanghuiwen.auth.controll;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanghuiwen.core.annotation.PowerEnable;
import com.wanghuiwen.core.controller.Ctrl;
import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultGenerator;
import com.wanghuiwen.auth.model.SysWhitelist;
import com.wanghuiwen.auth.service.SysWhitelistService;
import io.swagger.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
* Created by CodeGenerator on 2019/11/08.
*/
@PowerEnable(name = "白名单",url = "/sys/whitelist")
@Api(value = "白名单", tags = {"白名单"})
@RestController
@RequestMapping("/sys/whitelist")
public class SysWhitelistController extends Ctrl {
    @Resource
    private SysWhitelistService sysWhitelistService;

    @ApiOperation(value = "白名单添加", tags = {"白名单"}, notes = "白名单添加")
    @PostMapping(value="/add",name="白名单添加")
    @CacheEvict(value = "whiteList",key = "'whiteList'")
    public Result add(@ApiParam SysWhitelist sysWhitelist) {
        sysWhitelistService.save(sysWhitelist);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "白名单删除", tags = {"白名单"}, notes = "白名单删除")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",required=true, value = "白名单id", dataType = "Long", paramType = "query")
    })
    @PostMapping(value="/delete",name="白名单删除")
    @CacheEvict(value = "whiteList",key = "'whiteList'")
    public Result delete(@RequestParam String id) {
        sysWhitelistService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "白名单修改", tags = {"白名单"}, notes = "白名单修改,对象主键必填")
    @PostMapping(value="/update",name="白名单修改")
    @CacheEvict(value = "whiteList",key = "'whiteList'")
    public Result update(@ApiParam String url,
                         @ApiParam String id) {

        return sysWhitelistService.update(url,id);
    }

    @ApiOperation(value = "白名单列表信息", tags = {"白名单"}, notes = "白名单列表信息")
    @PostMapping(value="/list",name="白名单列表信息")
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);

        List<SysWhitelist> list = sysWhitelistService.selectAll();
        PageInfo<SysWhitelist> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
