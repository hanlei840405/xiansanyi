package com.bird.framework.xsy.rest;

import com.bird.framework.xsy.entity.User;
import com.bird.framework.xsy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jesse.Han
 */
@RestController
@RequestMapping("/api/user")
public class UserRestController {
    @Autowired
    private UserService userService;

    @RequestMapping("/username/{username}")
    public User username(@PathVariable("username") String username) {
        return userService.selectByUsername(username);
    }
}
