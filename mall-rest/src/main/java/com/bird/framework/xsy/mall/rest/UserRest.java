package com.bird.framework.xsy.mall.rest;

import com.bird.framework.xsy.mall.entity.User;
import com.bird.framework.xsy.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserRest {

    @Autowired
    private UserService userService;

    @RequestMapping("/username/{username}")
    public User username(@PathVariable("username") String username) {
        return userService.selectByUsername(username);
    }
}
