package com.wanghuiwen.blog.config;

import com.wanghuiwen.core.conifg.resource.AbsResourceServerConfig;
import com.wanghuiwen.core.conifg.resource.WhiteList;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends AbsResourceServerConfig {

    protected ResourceServerConfig(AuthorizationServerProperties properties) {
        super(properties);
    }

    public WhiteList whiteList() {
        WhiteList whiteList = new WhiteList();
        whiteList.getWhiteList().add("/generator/code");
        whiteList.getWhiteList().add("/generator/code/submit");
        whiteList.getWhiteList().add("/article/list/index");
        return whiteList;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
