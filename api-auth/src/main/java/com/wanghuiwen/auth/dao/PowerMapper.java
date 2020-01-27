package com.wanghuiwen.auth.dao;


import com.wanghuiwen.auth.model.Power;
import com.wanghuiwen.core.Mapper;
import com.wanghuiwen.core.mybatis.ResultMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PowerMapper extends Mapper<Power> {

    List<Power> getByUser(Long id);

    List<Power> getByRole(@Param("roleId") Long roleId);

    List<ResultMap<String, Object>> listAll();
}