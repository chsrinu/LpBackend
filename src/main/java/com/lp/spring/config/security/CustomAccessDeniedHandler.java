package com.lp.spring.config.security;

import com.lp.spring.exceptions.ExceptionStatus;
import com.lp.spring.model.ErrorResponse;
import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private Logger logger = Logger.getLogger(CustomAccessDeniedHandler.class);
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ErrorResponse response = new ErrorResponse();
        response.setMessage("You don't have Access to this URL");
        response.setStatus(ExceptionStatus.ACCESS_DENIED);
        logger.info(e.getMessage());
        SecurityUtils.sendJsonErrorResponse(httpServletResponse,response);
    }
}
