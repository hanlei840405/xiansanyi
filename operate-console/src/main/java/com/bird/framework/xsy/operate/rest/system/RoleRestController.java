package com.bird.framework.xsy.operate.rest.system;

import com.bird.framework.xsy.operate.entity.Role;
import com.bird.framework.xsy.operate.service.RoleService;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("rest/system/role")
public class RoleRestController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/id")
    public Role id(Long id) {
        return roleService.selectById(id);
    }

    @RequestMapping("/all")
    public List<Role> all() {
        return roleService.all();
    }

    @RequestMapping("/page")
    public Map<String, Object> page(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer rows) {
        Page<Role> menus = roleService.page(page, rows);
        Map<String, Object> result = Maps.newHashMap();
        result.put("total", menus.getTotal());
        result.put("rows", menus.getResult());
        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public int save(Role role) {
        roleService.save(role);
        return HttpStatus.OK.value();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(Long id) {
        roleService.delete(id);
        return HttpStatus.OK.value();
    }

    @RequestMapping(value = "/grant", method = RequestMethod.POST)
    public int grant(Long id, String checked, String indeterminate) {
        String[] checkedArary = checked.split(",");
        String[] indeterminateArary = indeterminate.split(",");
        List<Long> array1 = new ArrayList<>();
        List<Long> array2 = new ArrayList<>();
        for (String s : checkedArary) {
            if (!StringUtils.isEmpty(StringUtils.trimAllWhitespace(s))) {
                array1.add(Long.parseLong(s));
            }
        }
        for (String s : indeterminateArary) {
            if (!StringUtils.isEmpty(StringUtils.trimAllWhitespace(s))) {
                array2.add(Long.parseLong(s));
            }
        }
        roleService.grant(id, array1, array2);
        return HttpStatus.OK.value();
    }
}
