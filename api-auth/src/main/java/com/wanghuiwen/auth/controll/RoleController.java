package com.wanghuiwen.auth.controll;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanghuiwen.core.annotation.PowerEnable;
import com.wanghuiwen.core.controller.Ctrl;
import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultGenerator;
import com.wanghuiwen.auth.model.Role;
import com.wanghuiwen.auth.service.PowerService;
import com.wanghuiwen.auth.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
* Created by CodeGenerator on 2019/03/25.
*/
@PowerEnable(name = "角色管理",url = "/role")
@Api(value = "角色管理", tags = {"角色管理"})
@RestController
@RequestMapping("/role")
public class RoleController extends Ctrl {
    @Resource
    private RoleService roleService;
    @Resource
    private PowerService powerService;

    @ApiOperation(value = "添加角色", tags = {"角色管理"}, notes = "添加角色")
    @PostMapping(value = "/add",name = "添加角色")
    public Result add(Role role) {
        roleService.save(role);
        return ResultGenerator.genSuccessResult();
    }


    @ApiOperation(value = "添加角色", tags = {"角色管理"}, notes = "添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "条数", dataType = "Integer", paramType = "query"),
    })
    @PostMapping(value = "/list",name = "角色列表")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);

        List<Role> list = roleService.findAll();
        PageInfo<Role> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    @ApiOperation(value = "角色添加权限", tags = {"角色管理"}, notes = "角色添加权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "powers", value = "权限json", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", paramType = "query"),
    })
    @PostMapping(value = "/add/power",name = "角色添加权限")
    @CacheEvict(value = "power",key = "#roleId")
    public Result addPower(String powers, Long roleId){

        roleService.addPower( powers,roleId);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "获取角色权限", tags = {"角色管理"}, notes = "获取角色权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Long", paramType = "query"),
    })
    @PostMapping(value = "/get/power",name = "获取角色权限")
    public Result getPowers(Long roleId){
        List powers =  powerService.getByRole(roleId);
        return ResultGenerator.genSuccessResult(powers);
    }
}
