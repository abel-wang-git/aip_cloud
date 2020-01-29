package com.wanghuiwen.auth.config.resource;

import com.wanghuiwen.core.response.Result;
import com.wanghuiwen.core.response.ResultGenerator;
import com.wanghuiwen.util.JSONUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ResourceAccessDeniedHandler implements AccessDeniedHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        logger.error(e.getMessage(),e);

        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Result result = ResultGenerator.genForbiddenResult();
        response.setStatus(HttpStatus.SC_OK);
        PrintWriter printWriter = response.getWriter();
        printWriter.append(JSONUtils.obj2json(result));
    }
}
