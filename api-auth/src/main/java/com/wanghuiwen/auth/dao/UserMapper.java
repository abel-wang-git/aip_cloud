package com.wanghuiwen.auth.dao;


import com.wanghuiwen.auth.model.User;
import com.wanghuiwen.core.Mapper;

public interface UserMapper extends Mapper<User> {

    void deleteRoleById(Long userId);
}