package com.wanghuiwen.auth.service;

import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.service.Service;
import com.wanghuiwen.auth.model.SysWhitelist;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/11/08.
 */
public interface SysWhitelistService extends Service<SysWhitelist> {

    List<SysWhitelist> selectAll();

    Result update(String s, String url);
}
