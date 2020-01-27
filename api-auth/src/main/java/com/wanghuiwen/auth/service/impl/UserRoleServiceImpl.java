package com.wanghuiwen.auth.service.impl;

import com.wanghuiwen.core.service.AbstractService;
import com.wanghuiwen.auth.dao.UserRoleMapper;
import com.wanghuiwen.auth.model.UserRole;
import com.wanghuiwen.auth.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/03/25.
 */
@Service
@Transactional
public class UserRoleServiceImpl extends AbstractService<UserRole> implements UserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;

}
