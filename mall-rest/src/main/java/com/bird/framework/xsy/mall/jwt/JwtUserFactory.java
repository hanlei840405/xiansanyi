package com.bird.framework.xsy.mall.jwt;

import com.bird.framework.xsy.mall.entity.Member;
import com.bird.framework.xsy.mall.entity.PasswordRecord;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(Member member, PasswordRecord passwordRecord, List<String> authorities) {
        return new JwtUser(
                member.getUsername(),
                member.getPassword(),
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
                passwordRecord == null ? null : passwordRecord.getCreated()
        );
    }
}

