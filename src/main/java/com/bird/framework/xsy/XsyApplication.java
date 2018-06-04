package com.bird.framework.xsy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.bird.framework.xsy.mapper")
public class XsyApplication {

	public static void main(String[] args) {
		SpringApplication.run(XsyApplication.class, args);
	}
}
