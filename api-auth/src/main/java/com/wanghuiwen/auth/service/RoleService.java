package com.wanghuiwen.auth.service;


import com.wanghuiwen.core.service.Service;
import com.wanghuiwen.auth.model.Role;

/**
 * Created by CodeGenerator on 2019/03/25.
 */
public interface RoleService extends Service<Role> {

    void addPower(String powers, Long roleId);
}
