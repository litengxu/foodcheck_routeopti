package com.bjfu.fcro.config.handler;

import com.alibaba.fastjson.JSON;
import com.bjfu.fcro.common.entity.JsonResult;
import com.bjfu.fcro.config.utils.TokenCache;
import com.bjfu.fcro.entity.SysUser;
import com.bjfu.fcro.service.SysRoleService;
import com.bjfu.fcro.service.SysUserService;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.config.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Program: ltx_web
 * @ClassName: CustomizeAuthenticationSuccessHandler
 * @Author: 李腾旭
 * @Date: 2020-06-13 08:34
 * @Description: 登录成功处理逻辑
 * @Version: V1.0
 *
 *
 */
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //更新用户表上次登录时间、更新人、更新时间等字段
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser sysUser = sysUserService.selectByAccount(userDetails.getUsername());
        sysUser.setLast_login_time(new Date());
        sysUser.setUpdate_user(sysUser.getId());
        sysUserService.update(sysUser);

        //此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
        //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展

        SecurityContextHolder.getContext().setAuthentication(authentication);
        //取token
        //好的解决方案，登录成功后token存储到数据库中
        //只要token还在过期内，不需要每次重新生成
        //先去缓存中找
        String token = TokenCache.getTokenFromCache(userDetails.getUsername());
        if(token ==null) {
            System.out.println("初次登录，token还没有，生成新token。。。。。。");
            //如果token为空，则去创建一个新的token

            token = jwtTokenUtil.generateToken(userDetails);
            //把新的token存储到缓存中
            TokenCache.setToken(userDetails.getUsername(),token);
        }

        //加载前端菜单
//        List<SysFrontendMenuTable> menus = service.getMenusByUserName(userDetails.getUsername());
//        //
//        Map<String,Object> map = new HashMap<>();
//        map.put("username",userDetails.getUsername());
//        map.put("auth",userDetails.getAuthorities());
//        map.put("menus",menus);
//        map.put("token",token);
        String role = sysRoleService.selectroleByAccount(userDetails.getUsername());
        Map<String,Object> map = new HashMap<>();
        map.put("rolename",role);
        map.put("token",token);
        //返回json数据
        JsonResult result = ResultTool.success(map);
        //处理编码方式，防止中文乱码的情况
        response.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        response.getWriter().write(JSON.toJSONString(result));

    }
}

