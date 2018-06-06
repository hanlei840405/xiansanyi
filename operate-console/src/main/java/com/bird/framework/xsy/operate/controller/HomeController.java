package com.bird.framework.xsy.operate.controller;

import com.bird.framework.xsy.operate.entity.User;
import com.bird.framework.xsy.operate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jesse.Han
 */
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String portal(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.selectByUsername(username);
        model.addAttribute("user", user);
        return "portal";
    }
}
