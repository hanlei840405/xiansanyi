package com.bird.framework.xsy.service;

import com.bird.framework.xsy.entity.Role;
import com.bird.framework.xsy.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.selectByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
        }
        String password = user.getPassword();
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> roleVos = roleService.selectByUsername(s);
        roleVos.forEach(roleVo -> authorities.add(new SimpleGrantedAuthority(roleVo.getCode())));

        UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(s, password, authorities);
        return userDetails;
    }
}
