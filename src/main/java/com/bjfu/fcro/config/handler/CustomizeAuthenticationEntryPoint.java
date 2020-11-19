package com.bjfu.fcro.config.handler;

import com.alibaba.fastjson.JSON;
import com.bjfu.fcro.common.entity.JsonResult;
import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.ResultTool;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Program: ltx_web
 * @ClassName: CustomizeAuthenticationEntryPoint
 * @Author: 李腾旭
 * @Date: 2020-06-12 18:37
 * @Description: ${todo}
 * @Version: V1.0
 * 屏蔽匿名访问时登录重定向，并返回封装好的json
 */
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        JsonResult result = ResultTool.fail(ResultCode.USER_NOT_LOGIN);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
