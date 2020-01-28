package com.wanghuiwen.core.config.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

public class AuthAccessDecisionManager implements AccessDecisionManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String ROLE_ADMIN="admin";
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        if(configAttributes == null  || configAttributes.size()==0) {
            throw new AccessDeniedException("permission denied");
        }
        ConfigAttribute cfa;
        String needRole;
        //遍历基于URL获取的权限信息和用户自身的角色信息进行对比.
        for(Iterator<ConfigAttribute> it = configAttributes.iterator(); it.hasNext();) {
            cfa = it.next();
            needRole = cfa.getAttribute();
            //authentication 为CustomUserDetailService中添加的权限信息.
            for(GrantedAuthority grantedAuthority:authentication.getAuthorities()) {
                if(needRole.equals(grantedAuthority.getAuthority())||grantedAuthority.getAuthority().equals(ROLE_ADMIN)) {
                    logger.info(authentication.getName()+":"+needRole);
                    return;
                }
            }
        }
        logger.error(authentication.getName()+":访问非法权限:"+configAttributes);
        throw new AccessDeniedException("permission denied");
    }
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
