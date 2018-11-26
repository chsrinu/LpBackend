package com.lp.spring.config.security;

import com.lp.spring.exceptions.ExceptionStatus;
import com.lp.spring.model.ErrorResponse;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    private Logger logger = Logger.getLogger(CustomAuthEntryPoint.class);
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ErrorResponse response = new ErrorResponse();
        if(e.getClass().getSimpleName().equals(BadCredentialsException.class.getSimpleName())){
            logger.info("Invalid Credentials for request :"+httpServletRequest.getHeader("Authorization"));
            response.setMessage(e.getMessage());
            response.setStatus(ExceptionStatus.INVALID_CREDENTIALS);
        }
        else if(e.getClass().getSimpleName().equals(InsufficientAuthenticationException.class.getSimpleName())){
            logger.info("Cannot proceed with empty credentials");
            response.setMessage(e.getMessage());
            response.setStatus(ExceptionStatus.MISSING_CREDENTIALS);
        }
        SecurityUtils.sendJsonErrorResponse(httpServletResponse,response);
    }
}
