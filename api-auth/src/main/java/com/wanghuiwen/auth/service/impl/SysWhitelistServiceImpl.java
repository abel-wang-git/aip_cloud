package com.wanghuiwen.auth.service.impl;

import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultGenerator;
import com.wanghuiwen.core.service.AbstractService;
import com.wanghuiwen.auth.dao.SysWhitelistMapper;
import com.wanghuiwen.auth.model.SysWhitelist;
import com.wanghuiwen.auth.service.SysWhitelistService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/11/08.
 */
@Service
@Transactional
public class SysWhitelistServiceImpl extends AbstractService<SysWhitelist> implements SysWhitelistService {
    @Resource
    private SysWhitelistMapper sysWhitelistMapper;

    @Override
    @Cacheable(cacheNames = "whiteList",key = "'whiteList'")
    public List<SysWhitelist> selectAll() {
        return sysWhitelistMapper.selectAll();
    }

    @Override
    public Result update(String url, String id) {
        sysWhitelistMapper.update(url,id);
        return ResultGenerator.genSuccessResult();
    }
}
