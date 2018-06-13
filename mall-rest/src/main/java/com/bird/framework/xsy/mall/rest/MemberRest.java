package com.bird.framework.xsy.mall.rest;

import com.bird.framework.xsy.mall.entity.Member;
import com.bird.framework.xsy.mall.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberRest {

    @Autowired
    private MemberService memberService;

    @PreAuthorize("hasRole('BUYER')")
    @RequestMapping("/username/{username}")
    public Member username(@PathVariable("username") String username) {
        return memberService.selectByUsername(username);
    }
}
