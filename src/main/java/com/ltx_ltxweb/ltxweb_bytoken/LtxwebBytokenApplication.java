package com.ltx_ltxweb.ltxweb_bytoken;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.ltx_ltxweb.ltxweb_bytoken.dao")
@ServletComponentScan
public class LtxwebBytokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtxwebBytokenApplication.class, args);
	}

}
