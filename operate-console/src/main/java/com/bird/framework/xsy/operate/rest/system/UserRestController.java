package com.bird.framework.xsy.operate.rest.system;

import com.bird.framework.xsy.operate.entity.Role;
import com.bird.framework.xsy.operate.entity.User;
import com.bird.framework.xsy.operate.service.RoleService;
import com.bird.framework.xsy.operate.service.UserService;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author jesse.Han
 */
@RestController
@RequestMapping("rest/system/user")
public class UserRestController {
    @Autowired
    @Qualifier("operateUserService")
    private UserService userService;
    @Autowired
    @Qualifier("operateRoleService")
    private RoleService roleService;

    @RequestMapping("/id")
    public User id(Long id) {
        return userService.selectById(id);
    }

    @RequestMapping("/username/{username}")
    public User username(@PathVariable("username") String username) {
        return userService.selectByUsername(username);
    }

    @RequestMapping("/page")
    public Map<String, Object> page(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer rows) {
        Page<User> users = userService.page(page, rows);
        Map<String, Object> result = Maps.newHashMap();
        result.put("total", users.getTotal());
        result.put("rows", users.getResult());
        return result;
    }

    @RequestMapping("/roles")
    public List<Role> roles(Long id) {
        User user = userService.selectById(id);
        return roleService.selectByUsername(user.getUsername());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public int save(User user) {
        userService.save(user);
        return HttpStatus.OK.value();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(Long id) {
        userService.delete(id);
        return HttpStatus.OK.value();
    }

    @RequestMapping(value = "/grant", method = RequestMethod.POST)
    public int grant(Long id, String array) {
        String[] roleIds = array.split(",");
        userService.grant(id, roleIds);
        return HttpStatus.OK.value();
    }
}
