package com.bird.framework.xsy.operate.controller.mall;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mall")
public class MovieController {

    @RequestMapping("/movie")
    public String index() {
        return "mall/movie";
    }
}
