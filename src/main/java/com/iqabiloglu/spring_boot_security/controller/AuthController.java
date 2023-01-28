package com.iqabiloglu.spring_boot_security.controller;

import com.iqabiloglu.spring_boot_security.controller.dto.AuthDto;
import com.iqabiloglu.spring_boot_security.controller.dto.RefreshDto;
import com.iqabiloglu.spring_boot_security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

//

    @PostMapping(value = "/sign-in")
    public AuthDto signIn(Authentication authentication) {
        return authService.signIn(authentication);
    }

    @PostMapping(value = "/refresh")
    public AuthDto refresh(@RequestBody RefreshDto refreshToken) {
        return  authService.refreshToken(refreshToken);
    }
}
