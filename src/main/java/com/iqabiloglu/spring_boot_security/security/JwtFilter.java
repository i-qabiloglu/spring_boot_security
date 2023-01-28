package com.iqabiloglu.spring_boot_security.security;

import com.iqabiloglu.spring_boot_security.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private static final String BEARER_AUTH_HEADER = "Bearer ";
    private static final String ROLES_CLAIM = "roles";
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        log.info("Received request with header {}", request.getHeader("Authorization"));


        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith(BEARER_AUTH_HEADER)) {
            String token = authHeader.substring(BEARER_AUTH_HEADER.length()).trim();
            log.info("Token is {}", token);
            Claims claims = jwtService.parseToken(token);

            List<String> rolesList = claims.get(ROLES_CLAIM, List.class);
            Set<SimpleGrantedAuthority> roles = rolesList.stream()
                                                         .map(SimpleGrantedAuthority::new)
                                                         .collect(Collectors.toSet());

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(),
                                                                                               "", roles);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}

