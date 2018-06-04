package com.bird.framework.xsy;

import com.bird.framework.xsy.entity.User;
import com.bird.framework.xsy.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.bird.framework.xsy.mapper")
public class XsyApplication {

	public static void main(String[] args) {
		UserService userService = SpringApplication.run(XsyApplication.class, args).getBean(UserService.class);
//		User user = new User();
//		user.setFirstName("磊");
//		user.setLastName("韩");
//		user.setMobile("18615267773");
//		user.setNick("jesse");
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		user.setPassword(encoder.encode("123456"));
//		user.setStatus("1");
//		user.setVersion(1);
//		userService.save(user);
	}
}
