package com.wanghuiwen.auth.config.auth;

import com.wanghuiwen.core.model.AuthUser;
import com.wanghuiwen.util.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class AuthUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        AuthUser user =  (AuthUser) authentication.getPrincipal();
        response.put(USERNAME, JSONUtils.obj2json(user));
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }
}
