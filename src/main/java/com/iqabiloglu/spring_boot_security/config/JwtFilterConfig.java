package com.iqabiloglu.spring_boot_security.config;

import com.iqabiloglu.spring_boot_security.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class JwtFilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtFilter jwtFilter;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
