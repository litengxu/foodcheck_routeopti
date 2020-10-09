package com.ltx_ltxweb.ltxweb_bytoken.config.handler;

import com.alibaba.fastjson.JSON;
import com.ltx_ltxweb.ltxweb_bytoken.common.entity.JsonResult;
import com.ltx_ltxweb.ltxweb_bytoken.common.enums.ResultCode;
import com.ltx_ltxweb.ltxweb_bytoken.common.utils.ResultTool;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Program: ltx_web
 * @ClassName: CustomizeSessionInformationExpiredStrategy
 * @Author: 李腾旭
 * @Date: 2020-06-13 11:28
 * @Description: 会话信息过期策略
 * @Version: V1.0
 */
@Component
public class CustomizeSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        JsonResult result = ResultTool.fail(ResultCode.USER_ACCOUNT_USE_BY_OTHERS);
        HttpServletResponse httpServletResponse = sessionInformationExpiredEvent.getResponse();
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
