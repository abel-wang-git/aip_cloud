package com.wanghuiwen.blog.config;

import com.wanghuiwen.core.config.resource.AbsResourceServerConfig;
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

    @Override
    protected void setWhitelist() {
        whitelist.add("/article/list/index");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
