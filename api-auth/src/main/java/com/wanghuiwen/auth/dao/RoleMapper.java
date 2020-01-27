package com.wanghuiwen.auth.dao;



import com.wanghuiwen.auth.model.Role;
import com.wanghuiwen.core.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {
    List<Role> getByUser(Long userId);

    void deletePower(Long roleId);

    Role selectBydescr(String desc);
}