package com.wanghuiwen.auth.config;

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
        //白名单添加
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
