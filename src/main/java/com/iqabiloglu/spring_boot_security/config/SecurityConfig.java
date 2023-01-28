package com.iqabiloglu.spring_boot_security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilterConfig jwtFilterConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/v1/users/public").permitAll()
                .antMatchers("/auth/sign-in").authenticated()
                .antMatchers("/v1/users/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/v1/users/admin").hasAnyRole("ADMIN")
                .and()
                .httpBasic();
        http.apply(jwtFilterConfig);
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
