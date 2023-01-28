package com.iqabiloglu.spring_boot_security.service;

import com.iqabiloglu.spring_boot_security.controller.dto.AuthDto;
import com.iqabiloglu.spring_boot_security.controller.dto.JwtDto;
import com.iqabiloglu.spring_boot_security.controller.dto.RefreshDto;
import com.iqabiloglu.spring_boot_security.dao.entity.RefreshToken;
import com.iqabiloglu.spring_boot_security.dao.entity.User;
import com.iqabiloglu.spring_boot_security.dao.repository.RefreshTokenRepository;
import com.iqabiloglu.spring_boot_security.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthDto signIn(Authentication authentication) {
        JwtDto jwtDto = new JwtDto(jwtService.issueToken(authentication, Duration.ofHours(1)));
        RefreshDto refreshToken = create(authentication);
        return new AuthDto(jwtDto, refreshToken);
    }

    private RefreshDto create(Authentication authentication) {
        log.info("ActionLog.create.start");
        String token = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setValid(true);
        refreshToken.setDate(Date.from(Instant.now().plus(Duration.ofHours(1))));
        User user = userRepository.findByUsername(authentication.getName())
                                  .orElseThrow(( ) -> new UsernameNotFoundException("User not found"));
        refreshToken.setUser(user);

        refreshTokenRepository.save(refreshToken);


        log.info("ActionLog.create.end refresh token: {}", refreshToken.getToken());
        return new RefreshDto(token);
    }


    public AuthDto refreshToken(RefreshDto refreshTokenDto) {
        System.out.println(refreshTokenRepository.findAll());

        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenDto.getRefreshToken())
                                                          .orElseThrow(( ) -> new RuntimeException("Token not found"));
        refreshToken.setValid(false);
        refreshTokenRepository.save(refreshToken);

        User user = refreshToken.getUser();

        if (!user.isEnabled() || !user.isAccountNonExpired() || !user.isCredentialsNonExpired()) {
            throw new RuntimeException("User is locked ");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                "", user.getAuthorities());

        RefreshDto tokenDto = create(authenticationToken);

        JwtDto jwt = new JwtDto(jwtService.issueToken(authenticationToken, Duration.ofHours(1)));
        return new AuthDto(jwt, tokenDto);
    }
}
