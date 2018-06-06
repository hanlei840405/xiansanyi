package com.bird.framework.xsy.operate.rest.system;

import com.bird.framework.xsy.operate.entity.Menu;
import com.bird.framework.xsy.operate.service.MenuService;
import com.bird.framework.xsy.operate.vo.Tree;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("rest/system/menu")
public class MenuRestController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/page")
    public Map<String, Object> page(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer rows) {
        Page<Menu> menus = menuService.findAll(page, rows);
        Map<String, Object> result = Maps.newHashMap();
        result.put("total", menus.getTotal());
        result.put("rows", menus.getResult());
        return result;
    }

    @RequestMapping("/tree")
    public List<Tree<Long>> tree(@RequestParam(defaultValue = "0") long id) {
        List<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        List<Menu> menus = menuService.tree(id, roles);
        List<Tree<Long>> trees = Lists.newArrayList();
        menus.stream().forEach(menu -> {
            Map<String, Object> attributes = Maps.newHashMap();
            attributes.put("code", menu.getCode());
            attributes.put("url", menu.getUrl());
            trees.add(new Tree<>(menu.getId(), menu.getName(), null, attributes));
        });
        return trees;
    }
}
