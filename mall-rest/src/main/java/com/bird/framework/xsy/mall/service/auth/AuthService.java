package com.bird.framework.xsy.mall.service.auth;

import com.bird.framework.xsy.mall.entity.Member;
import com.bird.framework.xsy.mall.enums.RoleEnum;
import com.bird.framework.xsy.mall.jwt.JwtTokenHelper;
import com.bird.framework.xsy.mall.jwt.JwtUserFactory;
import com.bird.framework.xsy.mall.service.MemberService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MemberService memberService;

    public String signIn(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenHelper.generateToken(userDetails);
        return token;
    }

    public String signUp(Member member) {
        final String username = member.getUsername();
        if (memberService.selectByUsername(username) != null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = member.getPassword();
        member.setPassword(encoder.encode(rawPassword));
        memberService.save(member);
        final UserDetails userDetails = JwtUserFactory.create(member, null, Lists.newArrayList(RoleEnum.getName(member.getRole())));
        final String token = jwtTokenHelper.generateToken(userDetails);
        return token;
    }
}
