package com.iqabiloglu.spring_boot_security.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {

    private JwtDto accessToken;
    private RefreshDto refreshToken;
}