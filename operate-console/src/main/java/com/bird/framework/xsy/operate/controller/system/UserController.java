package com.bird.framework.xsy.operate.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system")
public class UserController {

    @RequestMapping("/user")
    public String index() {
        return "system/user";
    }
}
