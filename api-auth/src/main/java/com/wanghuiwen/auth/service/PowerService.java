package com.wanghuiwen.auth.service;


import com.wanghuiwen.core.mybatis.ResultMap;
import com.wanghuiwen.core.service.Service;
import com.wanghuiwen.auth.model.Power;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/03/25.
 */
public interface PowerService extends Service<Power> {

    @Override

    List<Power> findAll();

    List<ResultMap<String,Object>> listAll();

    List<Power>  getByRole(Long roleId);
}
