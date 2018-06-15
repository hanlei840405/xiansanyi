package com.bird.framework.xsy.mall.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeView {

    @RequestMapping("")
    public String home() {
        return "portal";
    }
}
