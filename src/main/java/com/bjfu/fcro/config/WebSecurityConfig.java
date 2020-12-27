package com.bjfu.fcro.config;

import com.bjfu.fcro.config.handler.*;
import com.bjfu.fcro.config.service.UserDetailsServiceImpl;
import com.bjfu.fcro.config.fliter.VerifyCodeFilter;
import com.bjfu.fcro.config.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    认证
    //匿名用户访问无权限资源时的异常
    @Autowired
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    //登录成功处理逻辑
    @Autowired
    CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    //推出处理逻辑
    @Autowired
    CustomizeLogoutHandler customizeLogoutHandler;
    //登录失败处理逻辑
    @Autowired
    CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    //登出成功处理逻辑
    @Autowired
    CustomizeLogoutSuccessHandler logoutSuccessHandler;

    //会话失效(账号被挤下线)处理逻辑
    @Autowired
    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    //权限拒绝处理逻辑
    @Autowired
    CustomizeAccessDeniedHandler accessDeniedHandler;
    //访问决策管理器
    @Autowired
    CustomizeAccessDecisionManager accessDecisionManager;

    //实现权限拦截
    @Autowired
    CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;

    //登陆前先验证token
    @Autowired
    CustomizeOncePerRequestFilter customizeOncePerRequestFilter;

    @Autowired
    VerifyCodeFilter verifyCodeFilter;
    @Bean
    public UserDetailsService userDetailsService() {
        //获取用户账号密码及权限信息
        return new UserDetailsServiceImpl();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 设置默认的加密方式（强hash方式加密）
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //第1步：解决跨域问题。cors 预检请求放行,让Spring security 放行所有preflight request（cors 预检请求）
        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();


        //第2步：让Security永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
        //配置session创建的方式，默认时ifrequired（需要时再创建），token时可以使用stateless
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().cacheControl();

//        //未登录到转到登录界面..定制登录页面
//        http.formLogin().loginPage("/login");
//        http.csrf().disable();//关闭crsf功能，登录失败或者注销失败存在的愿意
//        //注销
//        http.logout().logoutSuccessUrl("/");
//        //开启记住我功能 保存cookies客户端 session 服务器端 默认保存两周
//        http.rememberMe().rememberMeParameter("");

        //拥有query_user权限的可以访问url
        http.cors().and().csrf().disable();
        //当需要验证码时打开这个注释 /vercode
//        http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests().
//                withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//                        o.setAccessDecisionManager(accessDecisionManager);//决策管理器
//                        o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
//                        return o;
//                    }
//                }).
                //hasrole("")
//        hasAnyAuthority("delete_user","query_user") 拥有其中一个权限就可以访问
                antMatchers("/user/deleteUser/**").hasAuthority("delete_user").
                antMatchers("/user/getUser/**").hasAuthority("query_user").
//                antMatchers("/user/").
                //修改管理员信息和权限，需要超级管理员的权限
                antMatchers("/superuser/**").hasAnyAuthority("superadmin").
                //抽检员信息的管理，需要管理员权限
                antMatchers("/siinformation/**").hasAnyAuthority("admin").
                antMatchers("/ssaccount/**").hasAnyAuthority("admin").
                antMatchers("/index").permitAll().
                anyRequest().permitAll();


        //第4步：拦截token，并检测。在 UsernamePasswordAuthenticationFilter 之前添加 JwtAuthenticationTokenFilter
        http.addFilterBefore(customizeOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class);

        //第5步：拦截账号、密码。覆盖 UsernamePasswordAuthenticationFilter过滤器
//        http.addFilterAt(myUsernamePasswordAuthenticationFilter() , UsernamePasswordAuthenticationFilter.class);




                http.logout().
//                logoutUrl("/logoutnew ").
//                logoutSuccessUrl("/logoutsuccessurl").
                permitAll().//允许所有用户
                addLogoutHandler(customizeLogoutHandler).
                logoutSuccessHandler(logoutSuccessHandler).//登出成功处理逻辑
//                addLogoutHandler(logoutHandler).//无论推出是否成功，都要做一些逻辑处理
//                invalidateHttpSession(true).//是否在退出时让httpsession无效，默认为true
//                deleteCookies("JSESSIONID").//登出之后删除cookie
//      登入
        and().formLogin().
                permitAll().//允许所有用户
                successHandler(authenticationSuccessHandler).//登录成功处理逻辑
                failureHandler(authenticationFailureHandler).//登录失败处理逻辑

                //异常处理(权限拒绝、登录失效等)
       and().exceptionHandling().
                accessDeniedHandler(accessDeniedHandler).//权限拒绝处理逻辑
                authenticationEntryPoint(authenticationEntryPoint).//匿名用户访问无权限资源时的异常处理
        //会话管理
        and().sessionManagement().
                maximumSessions(1).//同一账号同时登录最大用户数
                expiredSessionStrategy(sessionInformationExpiredStrategy);//会话信息过期策略会话信息过期策略(账号被挤下线)


    }

    //授权
    //新版的spring security 密码需要编码
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).authorities("query_user")
//                .and();
//        auth.userDetailsService()
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
//        super.configure(auth);
    }

    /**
     * 手动注册账号、密码拦截器
     * @return
     * @throws Exception
//     */
//    @Bean
//    CustomizeForUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
//        CustomizeForUsernamePasswordAuthenticationFilter filter = new CustomizeForUsernamePasswordAuthenticationFilter();
//        //成功后处理
//        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
//        //失败后处理
//        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
//
//        filter.setAuthenticationManager(authenticationManagerBean());
//        return filter;
//    }
}
