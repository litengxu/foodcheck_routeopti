package com.bjfu.fcro.config.handler;

import com.alibaba.fastjson.JSON;
import com.bjfu.fcro.common.entity.JsonResult;
import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.ResultTool;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Program: ltx_web
 * @ClassName: CustomizeAccessDeniedHandler
 * @Author: 李腾旭
 * @Date: 2020-06-13 11:31
 * @Description: 权限拒绝处理逻辑
 * @Version: V1.0
 */
@Component
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
//        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        JsonResult result = ResultTool.fail(ResultCode.NO_PERMISSION);
        httpServletResponse.setContentType("text/json;charset=utf-8");


        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
