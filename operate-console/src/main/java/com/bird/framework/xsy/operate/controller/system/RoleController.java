package com.bird.framework.xsy.operate.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system")
public class RoleController {

    @RequestMapping("/role")
    public String index() {
        return "system/role";
    }
}