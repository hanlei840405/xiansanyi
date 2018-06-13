package com.bird.framework.xsy.operate.rest.mall;

import com.bird.framework.xsy.mall.entity.Member;
import com.bird.framework.xsy.mall.service.MemberService;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("rest/mall/member")
public class MemberRestController {
    @Autowired
    private MemberService memberService;

    @RequestMapping("/page")
    public Map<String, Object> page(String role, String gender, String mobile, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer rows) {
        Page<Member> menus = memberService.page(role, gender, mobile, page, rows);
        Map<String, Object> result = Maps.newHashMap();
        result.put("total", menus.getTotal());
        result.put("rows", menus.getResult());
        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(Long id) {
        memberService.delete(id);
        return HttpStatus.OK.value();
    }
}
