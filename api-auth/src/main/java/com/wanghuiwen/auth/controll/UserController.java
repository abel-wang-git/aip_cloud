package com.wanghuiwen.auth.controll;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanghuiwen.auth.config.AuthUser;
import com.wanghuiwen.core.annotation.PowerEnable;
import com.wanghuiwen.core.controller.Ctrl;
import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultGenerator;
import com.wanghuiwen.auth.model.Role;
import com.wanghuiwen.auth.model.User;
import com.wanghuiwen.auth.service.UserService;
import com.wanghuiwen.util.JSONUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/03/25.
 */
@PowerEnable(name = "账号管理",url = "/user")
@Api(value = "账号管理", tags = {"账号管理"})
@RestController
@RequestMapping(value = "/user")
public class UserController extends Ctrl {
    @Resource
    private UserService userService;


    @ApiOperation(value = "注册游客账号", tags = {"账号管理"}, notes = "注册游客账号")
    @PostMapping(value = "/registered", name = "用户添加")
    public Result add(@RequestParam String username ,
                      @RequestParam String password ,
                      @RequestParam String nickname ,
                      @RequestParam String avatar) {
        User user = new User();
        user.setUsername(username);
        user.setPassword( new BCryptPasswordEncoder().encode(password));
        user.setNickname(nickname);
        user.setAvatar(avatar);
        return userService.addVisitor(user);
    }

    @ApiOperation(value = "用户列表", tags = {"账号管理"}, notes = "用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "where", value = "条件json", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "条数", dataType = "Integer", paramType = "query"),
    })
    @PostMapping(value = "/list", name = "用户列表")
    @ResponseBody
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);

        Condition c = new Condition(User.class);
        Example.Criteria criteria = c.createCriteria();


        List<User> list = userService.findByCondition(c);
        return ResultGenerator.genSuccessResult(new PageInfo<>(list));
    }

    @ApiOperation(value = "用户添加角色", tags = {"账号管理"}, notes = "用户添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roles", value = "角色json", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", paramType = "query"),
    })
    @PostMapping(value = "/add/role", name = "用户添加角色")
    @Caching(evict = {@CacheEvict(value = "role", key = "#userId"), @CacheEvict(value = "power", key = "#userId")})
    public Result addRole(String roles, Long userId) {
        List<Long> roleids = JSONUtils.json22list(roles, Long.class);


        return userService.addRole(roleids, userId);
    }

    @PostMapping(value = "/get/role", name = "获取用户角色")
    public Result getRole(@RequestParam Long userId) {
        List<Role> roles = userService.getRole(userId);
        return ResultGenerator.genSuccessResult(roles);
    }

    @ApiOperation(value = "获取登录用户信息", tags = {"账号管理"}, notes = "获取登录用户信息")
    @PostMapping(value = "get", name = "获取登录用户信息")
    public Result get(Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        authUser.setPassword("");
        return ResultGenerator.genSuccessResult(authUser);
    }

    /**
     * 绑定微信
     *
     * @param openid openid
     */
    @ApiOperation(value = "绑定微信", tags = {"账号管理"}, notes = "绑定微信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "openid", dataType = "String", paramType = "query"),
    })
    @PostMapping(value = "wx/bind", name = "绑定微信")
    public Result bindWx(@RequestParam String openid, Authentication authentication) {

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        User user = userService.findById(authUser.getId());

        if (user.getOpenid() != null) return ResultGenerator.genFailResult("用户以绑定微信");

        user.setOpenid(openid);

        userService.update(user);

        return ResultGenerator.genSuccessResult();
    }

}
