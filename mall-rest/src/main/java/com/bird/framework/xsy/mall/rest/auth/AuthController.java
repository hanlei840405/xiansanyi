package com.bird.framework.xsy.mall.rest.auth;

import com.bird.framework.xsy.mall.entity.User;
import com.bird.framework.xsy.mall.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-in")
    public String singIn(String username, String password) {
        // Return the token
        return authService.signIn(username, password);
    }

    @PostMapping("/sign-up")
    public String signUp(User user) {
        return authService.signUp(user);
    }
}
