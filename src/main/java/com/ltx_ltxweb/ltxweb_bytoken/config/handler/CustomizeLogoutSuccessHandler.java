package com.ltx_ltxweb.ltxweb_bytoken.config.handler;

import com.alibaba.fastjson.JSON;
import com.ltx_ltxweb.ltxweb_bytoken.common.entity.JsonResult;

import com.ltx_ltxweb.ltxweb_bytoken.common.utils.ResultTool;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Program: ltx_web
 * @ClassName: CustomizeLogoutSuccessHandler
 * @Author: 李腾旭
 * @Date: 2020-06-13 11:11
 * @Description: ${todo}
 * @Version: V1.0
 */
@Component
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        JsonResult result = ResultTool.success();
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}

