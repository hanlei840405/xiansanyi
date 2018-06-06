package com.bird.framework.xsy.mall.service.auth;

import com.bird.framework.xsy.mall.entity.Role;
import com.bird.framework.xsy.mall.entity.User;
import com.bird.framework.xsy.mall.jwt.JwtTokenHelper;
import com.bird.framework.xsy.mall.jwt.JwtUserFactory;
import com.bird.framework.xsy.mall.service.RoleService;
import com.bird.framework.xsy.mall.service.UserService;
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

import java.util.stream.Collectors;

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
    private UserService userService;

    @Autowired
    private RoleService roleService;

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

    public String signUp(User user) {
        final String username = user.getUsername();
        if (userService.selectByUsername(username) != null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));
        userService.save(user);
        final UserDetails userDetails = JwtUserFactory.create(user, null, roleService.selectByUsername(username).stream().map(Role::getCode).collect(Collectors.toList()));
        final String token = jwtTokenHelper.generateToken(userDetails);
        return token;
    }
}
