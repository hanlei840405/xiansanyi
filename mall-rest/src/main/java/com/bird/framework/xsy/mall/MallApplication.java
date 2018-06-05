package com.bird.framework.xsy.mall;

import com.bird.framework.xsy.mall.config.ServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@Import(ServiceConfig.class)
public class MallApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(MallApplication.class, args);
//        UserService userService = configurableApplicationContext.getBean(UserService.class);
//        RoleService roleService = configurableApplicationContext.getBean(RoleService.class);
//        Role role = new Role();
//        role.setName("BUYER");
//        role.setStatus("1");
//        role.setVersion(1);
//        roleService.save(role);
//        User user = new User();
//        user.setFirstName("磊");
//        user.setLastName("韩");
//        user.setMobile("18615267773");
//        user.setNick("jesse");
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        user.setPassword(encoder.encode("123456"));
//        user.setStatus("1");
//        user.setVersion(1);
//        user.getRoles().add(role);
//        userService.save(user);
    }
}
