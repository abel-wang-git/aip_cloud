package com.wanghuiwen.auth.config;

import com.wanghuiwen.core.config.resource.AuthExceptionEntryPoint;
import com.wanghuiwen.core.config.resource.ResourceAccessDeniedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(AuthorizationServerProperties.class)
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AuthorizationServerProperties properties;
    @Resource
    private OAuth2ClientProperties oAuth2ClientProperties;

    public ResourceServerConfig(AuthorizationServerProperties properties) {
        this.properties = properties;
    }


    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("test_key");
        return converter;
    }
    @Bean
    public RemoteTokenServices remoteTokenServices(){
        logger.debug(properties.getCheckTokenAccess());
        RemoteTokenServices remoteTokenServices  = new RemoteTokenServices();
        remoteTokenServices.setClientId(oAuth2ClientProperties.getClientId());
        remoteTokenServices.setClientSecret(oAuth2ClientProperties.getClientSecret());
        remoteTokenServices.setAccessTokenConverter(jwtAccessTokenConverter());
        remoteTokenServices.setCheckTokenEndpointUrl(properties.getCheckTokenAccess());
        return remoteTokenServices;
    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
        resources.accessDeniedHandler(new ResourceAccessDeniedHandler());
        resources.tokenStore(tokenStore());
        resources.tokenServices(remoteTokenServices());
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
            fsi.setSecurityMetadataSource(new AuthMetadataSource());
            return fsi;
        }
    }
}
