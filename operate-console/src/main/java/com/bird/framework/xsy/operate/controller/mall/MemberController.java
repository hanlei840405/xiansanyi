package com.bird.framework.xsy.operate.controller.mall;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mall")
public class MemberController {

    @RequestMapping("/member")
    public String index() {
        return "mall/member";
    }
}
