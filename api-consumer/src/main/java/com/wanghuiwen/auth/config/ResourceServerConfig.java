package com.wanghuiwen.auth.config;

import com.wanghuiwen.core.config.resource.AuthExceptionEntryPoint;
import com.wanghuiwen.core.config.resource.ResourceAccessDeniedHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
        resources.accessDeniedHandler(new ResourceAccessDeniedHandler());
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
