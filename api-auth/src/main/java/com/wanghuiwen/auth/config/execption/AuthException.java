package com.wanghuiwen.auth.config.execption;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
@JsonSerialize(using = OAuthExceptionJacksonSerializer.class)
public class AuthException extends OAuth2Exception {
    public AuthException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthException(String msg) {
        super(msg);
    }
}
