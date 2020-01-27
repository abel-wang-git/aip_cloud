package com.wanghuiwen.auth.service.impl;

import com.wanghuiwen.core.service.AbstractService;
import com.wanghuiwen.auth.dao.RolePowerMapper;
import com.wanghuiwen.auth.model.RolePower;
import com.wanghuiwen.auth.service.RolePowerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/03/25.
 */
@Service
@Transactional
public class RolePowerServiceImpl extends AbstractService<RolePower> implements RolePowerService {
    @Resource
    private RolePowerMapper rolePowerMapper;

}
