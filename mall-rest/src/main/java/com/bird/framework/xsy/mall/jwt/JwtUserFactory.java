package com.bird.framework.xsy.mall.jwt;

import com.bird.framework.xsy.mall.entity.Role;
import com.bird.framework.xsy.mall.entity.User;
import com.bird.framework.xsy.mall.entity.UserPasswordRecord;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user, UserPasswordRecord userPasswordRecord, List<Role> roles) {
        return new JwtUser(
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(roles.stream().map(Role::getCode).collect(Collectors.toList())),
                userPasswordRecord == null ? null : userPasswordRecord.getCreated()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

