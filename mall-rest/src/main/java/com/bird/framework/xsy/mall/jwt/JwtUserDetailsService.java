package com.bird.framework.xsy.mall.jwt;

import com.bird.framework.xsy.mall.entity.User;
import com.bird.framework.xsy.mall.service.RoleService;
import com.bird.framework.xsy.mall.service.UserPasswordRecordService;
import com.bird.framework.xsy.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserPasswordRecordService userPasswordRecordService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user, userPasswordRecordService.selectLastByUsername(username), roleService.selectByUsername(username));
        }
    }
}
