package com.wanghuiwen.auth.config.resource;

import com.wanghuiwen.auth.model.SysWhitelist;
import com.wanghuiwen.auth.service.PowerService;
import com.wanghuiwen.auth.service.SysWhitelistService;
import com.wanghuiwen.core.config.resource.AuthExceptionEntryPoint;
import com.wanghuiwen.core.config.resource.ResourceAccessDeniedHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Resource
    private PowerService powerService;
    @Resource
    private SysWhitelistService sysWhitelistService;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
        resources.accessDeniedHandler(new ResourceAccessDeniedHandler());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //        "/oauth/**", "/login/**", "/logout/**","/user/registered"
        List<SysWhitelist> whitelists=sysWhitelistService.selectAll();
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,
                        whitelists.stream().map(SysWhitelist::getUrl).toArray(String[]::new))
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
            fsi.setSecurityMetadataSource(new AuthMetadataSource(powerService,sysWhitelistService));
            fsi.setAccessDecisionManager(new AuthAccessDecisionManager());
            return fsi;
        }

    }
}
