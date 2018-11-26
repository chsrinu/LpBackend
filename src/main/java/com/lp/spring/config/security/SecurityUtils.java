package com.lp.spring.config.security;

import com.lp.spring.model.ErrorResponse;
import com.lp.spring.utility.CommonUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SecurityUtils {
    public static void sendJsonErrorResponse(HttpServletResponse httpServletResponse, ErrorResponse response) throws IOException {
        PrintWriter out = httpServletResponse.getWriter();
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        out.print(CommonUtils.getJsonObject(response));
        out.flush();
    }
}
