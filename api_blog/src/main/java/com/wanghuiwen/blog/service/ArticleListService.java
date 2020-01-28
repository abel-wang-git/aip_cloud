package com.wanghuiwen.blog.service;

import com.wanghuiwen.blog.model.ArticleList;
import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.service.Service;


/**
 * Created by CodeGenerator on 2019/10/30.
 */
public interface ArticleListService extends Service<ArticleList> {
    Result update(Long id, String title, String content, String coverPicture, Long classId, Byte status, Boolean top);

    Result selectIndex(Integer page, Integer size);

    Result add(String title, String content, String coverPicture, Long classId, Byte status, Boolean top, String nickname, Long uid);
}
