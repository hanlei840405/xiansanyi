package com.bird.framework.xsy.mall.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movie")
public class MovieView {

    @RequestMapping("")
    public String index() {
        return "mall/movie";
    }
}
