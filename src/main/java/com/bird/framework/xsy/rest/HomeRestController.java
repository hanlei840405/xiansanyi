package com.bird.framework.xsy.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jesse.Han
 */
@RestController
@RequestMapping("/api")
public class HomeRestController {

    @RequestMapping("/state")
    public String state() {
        return "ok";
    }
}
