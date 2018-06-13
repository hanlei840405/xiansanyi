package com.bird.framework.xsy.operate;

import com.bird.framework.xsy.operate.entity.Menu;
import com.bird.framework.xsy.operate.service.MenuService;
import com.bird.framework.xsy.operate.service.RoleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
public class OperateApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(OperateApplication.class, args);
//        MenuService menuService = configurableApplicationContext.getBean(MenuService.class);
//        Menu system = new Menu();
//        system.setName("系统管理");
//        system.setCategory("MENU");
//        system.setParentId(0L);
//        system.setStatus("1");
//        system.setVersion(1);
//        menuService.save(system);
//        Menu menu = new Menu();
//        menu.setName("菜单管理");
//        menu.setCategory("NAVIGATION");
//        menu.setParentId(system.getId());
//        menu.setStatus("1");
//        menu.setVersion(1);
//        menuService.save(menu);
//        Menu role = new Menu();
//        role.setName("角色管理");
//        role.setCategory("NAVIGATION");
//        role.setParentId(system.getId());
//        role.setStatus("1");
//        role.setVersion(1);
//        menuService.save(role);
//        Menu user = new Menu();
//        user.setName("用户管理");
//        user.setCategory("NAVIGATION");
//        user.setParentId(system.getId());
//        user.setStatus("1");
//        user.setVersion(1);
//        menuService.save(user);
//        Menu operate = new Menu();
//        operate.setName("运营管理");
//        operate.setCategory("NAVIGATION");
//        operate.setParentId(0L);
//        operate.setStatus("1");
//        operate.setVersion(1);
//        menuService.save(operate);
//        Menu movie = new Menu();
//        movie.setName("影票交易");
//        movie.setCategory("NAVIGATION");
//        movie.setParentId(operate.getId());
//        movie.setStatus("1");
//        movie.setVersion(1);
//        menuService.save(movie);
//        Menu dinner = new Menu();
//        dinner.setName("餐票交易");
//        dinner.setCategory("NAVIGATION");
//        dinner.setParentId(operate.getId());
//        dinner.setStatus("1");
//        dinner.setVersion(1);
//        menuService.save(dinner);
//        Menu network = new Menu();
//        network.setName("话费流量交易");
//        network.setCategory("NAVIGATION");
//        network.setParentId(operate.getId());
//        network.setStatus("1");
//        network.setVersion(1);
//        menuService.save(network);
//		Member user = new Member();
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
