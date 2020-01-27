package com.wanghuiwen.auth.config.execption;

import cn.hutool.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

@Component
public class AuthExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        Throwable throwable = e.getCause();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ret", -1);
        jsonObject.put("msg", e.getMessage());
        return new ResponseEntity(jsonObject, HttpStatus.UNAUTHORIZED);
    }
}
