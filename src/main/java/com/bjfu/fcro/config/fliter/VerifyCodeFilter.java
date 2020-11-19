package com.bjfu.fcro.config.fliter;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.bjfu.fcro.common.entity.JsonResult;
import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.ResultTool;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Program: ltx_web
 * @ClassName: VerifyCodeFilter
 * @Author: 李腾旭
 * @Date: 2020-06-28 15:53
 * @Description: ${todo}
 * @Version: V1.0
 */
@Component
public class VerifyCodeFilter extends GenericFilterBean {
    private String defaultFilterProcessUrl = "/login";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        JsonResult result = null;
        if ("POST".equalsIgnoreCase(request.getMethod()) && defaultFilterProcessUrl.equals(request.getServletPath())) {
            // 验证码验证
            String requestCaptcha = request.getParameter("code");
            System.out.println(requestCaptcha);

            //处理编码方式，防止中文乱码的情况
            response.setContentType("text/json;charset=utf-8");
            String genCaptcha = (String) request.getSession().getAttribute("index_code");
            System.out.println(genCaptcha);
            if (StringUtils.isEmpty(requestCaptcha)) {
                result = ResultTool.fail(ResultCode.USER_CODE_ISEMPTY);
                //塞到HttpServletResponse中返回给前台
                response.getWriter().write(JSON.toJSONString(result));
                throw new AuthenticationServiceException("验证码不能为空!");
            }
            if (!genCaptcha.toLowerCase().equals(requestCaptcha.toLowerCase())) {
                result = ResultTool.fail(ResultCode.USER_CODE_ERRROR);
                //塞到HttpServletResponse中返回给前台
                response.getWriter().write(JSON.toJSONString(result));
                throw new AuthenticationServiceException("验证码错误!");
            }
        }


        chain.doFilter(request, response);
    }
}