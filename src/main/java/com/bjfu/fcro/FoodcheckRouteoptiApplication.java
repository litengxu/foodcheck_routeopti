package com.bjfu.fcro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.bjfu.fcro.dao")
@ServletComponentScan
@EnableTransactionManagement
@EnableAsync
public class FoodcheckRouteoptiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodcheckRouteoptiApplication.class, args);
	}

}
