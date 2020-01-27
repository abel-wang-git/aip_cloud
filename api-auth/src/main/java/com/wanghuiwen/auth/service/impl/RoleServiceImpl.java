package com.wanghuiwen.auth.service.impl;

import com.wanghuiwen.core.service.AbstractService;
import com.wanghuiwen.auth.dao.RoleMapper;
import com.wanghuiwen.auth.dao.RolePowerMapper;
import com.wanghuiwen.auth.model.Role;
import com.wanghuiwen.auth.model.RolePower;
import com.wanghuiwen.auth.service.RoleService;
import com.wanghuiwen.util.JSONUtils;
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
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RolePowerMapper rolePowerMapper;

    @Override
    @Transactional
    public void addPower(String powers,Long roleId) {
        roleMapper.deletePower(roleId);
        List<Long> powerids = JSONUtils.json2list(powers,Long.class);

        List<RolePower> rolePowers = new ArrayList<>();
        for (Long pid: powerids) {
            RolePower rolePower = new RolePower();
            rolePower.setPowerId(pid);
            rolePower.setRoleId(roleId);
            rolePowers.add(rolePower);
        }
        if(rolePowers.size()>0)
            rolePowerMapper.insertListNoAuto(rolePowers);
    }
}
