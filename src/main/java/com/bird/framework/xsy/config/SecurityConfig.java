package com.bird.framework.xsy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author jesse.Han
 */
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .anyRequest().hasRole("ADMIN")
                    .and()
                    .httpBasic();
        }
    }

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/css/**", "/", "/login").permitAll()
                    .and()
                    .formLogin()
                    .loginPage("/login").failureUrl("/")
                    .and()
                    .logout()
                    .invalidateHttpSession(true)
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .and()
                    .csrf();
        }
    }
}
