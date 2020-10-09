package com.ltx_ltxweb.ltxweb_bytoken.controller;

import com.ltx_ltxweb.ltxweb_bytoken.config.utils.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Program: ltx_web
 * @ClassName: VerifyCodeController
 * @Author: 李腾旭
 * @Date: 2020-06-28 15:21
 * @Description: ${todo}
 * @Version: V1.0
 */
//验证码
@RestController
public class VerifyCodeController {

    @GetMapping("/vercode")
    public void code(HttpServletRequest req, HttpServletResponse response) throws IOException {
        VerifyCode vc = new VerifyCode();
        BufferedImage image = vc.getImage();
        String text = vc.getText();
        HttpSession session = req.getSession();
        session.setAttribute("index_code", text);
        System.out.println("验证码为"+text);
        System.out.println("验证码为"+session.getAttribute("index_code"));
        response.setContentType("image/jpeg");
        // 禁止图片缓存
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        VerifyCode.output(image, response.getOutputStream());
    }
}
