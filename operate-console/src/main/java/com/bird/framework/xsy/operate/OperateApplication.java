package com.bird.framework.xsy.operate;

import com.bird.framework.xsy.operate.service.RoleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 300)
public class OperateApplication {

    public static void main(String[] args) {
        RoleService roleService = SpringApplication.run(OperateApplication.class, args).getBean(RoleService.class);
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
//        Role role = new Role();
//        role.setCode("ROLE_BIZ");
//        role.setName("运营");
//        role.setStatus("1");
//        role.setVersion(1);
//        roleService.save(role);
    }
}
