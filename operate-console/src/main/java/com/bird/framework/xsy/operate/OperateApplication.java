package com.bird.framework.xsy.operate;

import com.bird.framework.xsy.operate.config.ServiceConfig;
import com.bird.framework.xsy.operate.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@Import(ServiceConfig.class)
public class OperateApplication {

    public static void main(String[] args) {
        UserService userService = SpringApplication.run(OperateApplication.class, args).getBean(UserService.class);
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
