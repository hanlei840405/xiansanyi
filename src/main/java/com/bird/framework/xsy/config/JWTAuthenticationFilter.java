package com.bird.framework.xsy.config;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    private RedissonClient redissonClient;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, RedissonClient redissonClient) {
        super(authenticationManager);
        this.redissonClient = redissonClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("token");
        if (!StringUtils.isEmpty(header)) {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }else {
            chain.doFilter(request, response);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey("bird")
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            if (user != null) {
                RBucket<List<String>> rBucket = redissonClient.getBucket("authorities");
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (rBucket.get() != null) {
                    for (String authority : rBucket.get()) {
                        authorities.add(new SimpleGrantedAuthority(authority));
                    }
                }
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
            return null;
        }
        return null;
    }
}
