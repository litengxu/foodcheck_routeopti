package com.bjfu.fcro;

import com.bjfu.fcro.config.threadpools.scheduletask.TaskThreadPoolConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.bjfu.fcro.dao")
@ServletComponentScan
@EnableTransactionManagement
@EnableAsync
@EnableConfigurationProperties({TaskThreadPoolConfig.class} )
//@EnableScheduling
public class FoodcheckRouteoptiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodcheckRouteoptiApplication.class, args);
	}

}
