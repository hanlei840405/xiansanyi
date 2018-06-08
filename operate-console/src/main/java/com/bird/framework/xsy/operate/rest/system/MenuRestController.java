package com.bird.framework.xsy.operate.rest.system;

import com.bird.framework.xsy.operate.entity.Menu;
import com.bird.framework.xsy.operate.service.MenuService;
import com.bird.framework.xsy.operate.vo.Tree;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("rest/system/menu")
public class MenuRestController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/id")
    public Menu id(Long id) {
        return menuService.selectById(id);
    }

    @RequestMapping("/page")
    public Map<String, Object> page(@RequestParam(defaultValue = "0") Long id, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer rows) {
        Page<Menu> menus = menuService.page(id, page, rows);
        Map<String, Object> result = Maps.newHashMap();
        result.put("total", menus.getTotal());
        result.put("rows", menus.getResult());
        return result;
    }

    @RequestMapping("/tree")
    public List<Tree<Long>> tree(@RequestParam(defaultValue = "0") long id) {
        List<Menu> menus = menuService.tree(id);
        List<Tree<Long>> trees = Lists.newArrayList();
        menus.stream().forEach(menu -> {
            Map<String, Object> attributes = Maps.newHashMap();
            attributes.put("code", menu.getCode());
            attributes.put("url", menu.getUrl());
            trees.add(new Tree<>(menu.getId(), menu.getName(), null, attributes));
        });
        return trees;
    }

    @RequestMapping("/privileges")
    public List<Tree<Long>> privileges(@RequestParam(defaultValue = "0") long id) {
        List<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        List<Menu> menus = menuService.privileges(id, roles);
        List<Tree<Long>> trees = Lists.newArrayList();
        menus.stream().forEach(menu -> {
            Map<String, Object> attributes = Maps.newHashMap();
            attributes.put("code", menu.getCode());
            attributes.put("url", menu.getUrl());
            trees.add(new Tree<>(menu.getId(), menu.getName(), null, attributes));
        });
        return trees;
    }

    @RequestMapping("/allByRole")
    public List<Menu> allByRole(String code) {
        return menuService.allByRole(code);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public int save(Menu menu) {
        menuService.save(menu);
        return HttpStatus.OK.value();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(Long id) {
        menuService.delete(id);
        return HttpStatus.OK.value();
    }

    private List<Tree<Long>> convert2Tree(List<Menu> menus) {
        List<Tree<Long>> trees = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getParentId() == 0) {
                trees.add(findChildren(menu,menus));
            }
        }
        return trees;
    }

    private Tree<Long> findChildren(Menu menu, List<Menu> menus) {
        Tree<Long> tree = new Tree<>(menu.getId(), menu.getName(), null, );
        for (Menu node : menus) {
            if (node.getId().equals(node.getParentId())) {
                tree.getChildren().add(findChildren(node, trees));
            }
        }
        return tree;
    }
}
