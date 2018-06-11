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
import java.util.HashMap;
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
    public List<Tree<Long>> allByRole(String code) {
        return convert2Tree(menuService.allByRole(code));
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

    /**
     * @param menus 已按照parent排序
     * @return
     */
    private List<Tree<Long>> convert2Tree(List<Map<String, Object>> menus) {
        Map<Long, Tree<Long>> menuMap = new HashMap<>();
        List<Long> addedMenus = new ArrayList<>();
        menus.forEach(menu -> {
            if ((Long)menu.get("parent_id") == 0) { // 根级
                Tree<Long> root = new Tree<>();
                root.setId((Long) menu.get("id"));
                root.setParentId(0L);
                root.setText((String) menu.get("name"));
                root.getAttributes().put("url", menu.get("url"));
                String state = (String) menu.get("state");
                if ("1".equals(state)) {
                    root.setChecked(true);
                }
                menuMap.put((Long) menu.get("id"), root);
            } else { // 非根级,找到它的上级，追加到children中
                Tree<Long> parent = menuMap.get(menu.get("parent_id"));
                Tree<Long> node = new Tree<>();
                node.setId((Long) menu.get("id"));
                node.setParentId(node.getParentId());
                node.setText((String) menu.get("name"));
                node.getAttributes().put("url", menu.get("url"));
                String state = (String) menu.get("state");
                if ("1".equals(state)) {
                    node.setChecked(true);
                }
                if (!addedMenus.contains(node.getId())) {
                    parent.getChildren().add(node);
                    addedMenus.add(node.getId());
                }
            }
        });
        List<Tree<Long>> trees = new ArrayList<>();
        menuMap.values().forEach(value -> trees.add(value));
        return trees;
    }
}
