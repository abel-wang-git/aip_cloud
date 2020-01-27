package com.wanghuiwen.auth.service.impl;

import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultEnum;
import com.wanghuiwen.core.response.ResultGenerator;
import com.wanghuiwen.core.service.AbstractService;
import com.wanghuiwen.auth.dao.RoleMapper;
import com.wanghuiwen.auth.dao.UserMapper;
import com.wanghuiwen.auth.dao.UserRoleMapper;
import com.wanghuiwen.auth.model.Role;
import com.wanghuiwen.auth.model.User;
import com.wanghuiwen.auth.model.UserRole;
import com.wanghuiwen.auth.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/03/25.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    public static final String ROLE_VISITOR="visitor";

    @Override
    @Cacheable(cacheNames = "role", key = "#userId")
    public List<Role> getRole(Long userId) {
        return roleMapper.getByUser(userId);
    }

    @Override
    public Result addRole(List<Long> roles, Long userId) {
        User user = findById(userId);
        if (user == null) return ResultGenerator.genFailResult(ResultEnum.NO_CONTENT);

        List<UserRole> userRoles = new ArrayList<>();
        for (Long id : roles) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(id);
            userRoles.add(userRole);
        }

        userMapper.deleteRoleById(userId);
        if (userRoles.size() > 0)
            userRoleMapper.insertListNoAuto(userRoles);
        return ResultGenerator.genSuccessResult();
    }

    @Override
    public Result addVisitor(User user) {
        save(user);
        Role role = roleMapper.selectBydescr(ROLE_VISITOR);
        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(user.getId());
        userRoleMapper.insertSelective(userRole);



        return ResultGenerator.genSuccessResult();
    }
}
