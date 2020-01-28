package com.wanghuiwen.blog.service;
import com.wanghuiwen.core.config.authserver.AuthUser;
import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.service.Service;
import com.wanghuiwen.blog.model.ArticleContent;
import org.springframework.security.core.Authentication;


/**
 * Created by CodeGenerator on 2019/10/30.
 */
public interface ArticleContentService extends Service<ArticleContent> {

    Result detail(Integer id, AuthUser authUserId);
}
