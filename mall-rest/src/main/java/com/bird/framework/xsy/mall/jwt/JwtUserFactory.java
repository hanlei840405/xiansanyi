package com.bird.framework.xsy.mall.jwt;

import com.bird.framework.xsy.mall.entity.User;
import com.bird.framework.xsy.mall.entity.UserPasswordRecord;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user, UserPasswordRecord userPasswordRecord, List<String> authorities) {
        return new JwtUser(
                user.getUsername(),
                user.getPassword(),
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
                userPasswordRecord == null ? null : userPasswordRecord.getCreated()
        );
    }
}

