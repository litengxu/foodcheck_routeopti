package com.bjfu.fcro.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;

import java.io.File;
import java.util.regex.Pattern;
/**
 * 解决图片访问问题  拦截  /images/** 的接口请求 替换为 file:/saveFile/** 的请求 才能拿到本地的文件
 *
 * */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @SneakyThrows
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File pathRoot = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!pathRoot.exists()) {
            pathRoot = new File("");
        }
        String osName = System.getProperty("os.name");
        String saveFile = null;
        /*
        * window 下
        *       pathRoot E:\ltx\foodcheck_routeopti  可以用于存文件
        *       但是根据file取文件，需要用到 /,所以要把 \ 替换为 /
        * Linux 下
        *
        *   */
        if (Pattern.matches("Linux.*", osName)) {
            saveFile = pathRoot.getAbsolutePath().replace("%20"," ").replace('/', '/')+"/files/images"+"/";
            registry.addResourceHandler("/images/**").addResourceLocations("file:"+saveFile);
        } else if (Pattern.matches("Windows.*", osName)) {
            saveFile = pathRoot.getAbsolutePath().replace("%20"," ").substring(0,pathRoot.getAbsolutePath().replace("%20"," ").length()-15).replace('\\', '/')+"/files/images"+"/";
            registry.addResourceHandler("/images/**").addResourceLocations("file:/"+saveFile);
        }

//        registry.addResourceHandler("/images/**").addResourceLocations("file:/E:/ltx/foodcheck_routeopti/files/images");


    }

}

