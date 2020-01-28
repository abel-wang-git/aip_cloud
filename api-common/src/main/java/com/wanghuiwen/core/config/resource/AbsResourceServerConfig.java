package com.wanghuiwen.core.config.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(AuthorizationServerProperties.class)
public abstract class AbsResourceServerConfig implements ResourceServerConfigurer {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    protected List<String> whitelist = new ArrayList<>();

    @Resource
    private OAuth2ClientProperties oAuth2ClientProperties;
    @Resource
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    private final AuthorizationServerProperties properties;

    @Resource
    protected TokenStore tokenStore;

    protected AbsResourceServerConfig(AuthorizationServerProperties properties) {
        this.properties = properties;
    }


    private List<String> getWhitelist() {
        return whitelist;
    }

    /**
     * 设置白名单
     */
    protected abstract void setWhitelist();

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
        resources.accessDeniedHandler(new ResourceAccessDeniedHandler());
        resources.tokenStore(tokenStore);
        logger.debug(properties.getCheckTokenAccess());
        RemoteTokenServices remoteTokenServices  = new RemoteTokenServices();
        remoteTokenServices.setClientId(oAuth2ClientProperties.getClientId());
        remoteTokenServices.setClientSecret(oAuth2ClientProperties.getClientSecret());
        remoteTokenServices.setAccessTokenConverter(jwtAccessTokenConverter);
        remoteTokenServices.setCheckTokenEndpointUrl(properties.getCheckTokenAccess());
        resources.tokenServices(remoteTokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .withObjectPostProcessor(new AuthObjectPostProcessor())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and().logout().logoutUrl("/logout")
                .permitAll();
    }

    private class AuthObjectPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
        @Override
        public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
            fsi.setAccessDecisionManager(new AuthAccessDecisionManager());
            fsi.setSecurityMetadataSource(new AuthMetadataSource(getWhitelist()));
            return fsi;
        }
    }

}
