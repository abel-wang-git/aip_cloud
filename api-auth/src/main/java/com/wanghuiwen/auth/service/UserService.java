package com.wanghuiwen.auth.service;


import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.service.Service;
import com.wanghuiwen.auth.model.Role;
import com.wanghuiwen.auth.model.User;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/03/25.
 */
public interface UserService extends Service<User> {

    List<Role> getRole(Long userId);


    Result addRole(List<Long> roles, Long userId);

    Result addVisitor(User user);
}
