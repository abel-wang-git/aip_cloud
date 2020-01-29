package com.wanghuiwen.core.conifg.resource;

import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultGenerator;
import com.wanghuiwen.util.JSONUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        logger.error(e.getMessage(),e);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Result result = ResultGenerator.genUnauthorizedResult();
        httpServletResponse.setStatus(HttpStatus.SC_OK);
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.append(JSONUtils.obj2json(result));

    }
}
