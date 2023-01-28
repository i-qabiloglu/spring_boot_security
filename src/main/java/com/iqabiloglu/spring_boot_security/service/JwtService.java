package com.iqabiloglu.spring_boot_security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private String SECRET_KEY =
            "ZWp3YmRld2dmIHhhbXBsZSBvZiBqc3d0IGVuY29kaW5nIGtleSB0aGlzIGlzIGFuIGV4YW1wbGUgb2YganN3dCBlbmNvZGluZyBrZXkgdGhpcyBpcyBhbiBleGFtcGxlIG9mIGpzd3QgZW5jb2Rpbmcga2V5IA";


    public String issueToken(Authentication authentication, Duration duration) {

        final JwtBuilder jwtBuilder = Jwts.builder()
                                          .setSubject(authentication.getName())
                                          .setIssuedAt(new Date())
                                          .setExpiration(Date.from(Instant.now().plus(duration)))
                                          .setHeader(Map.of("type", "JWT"))
                                          .signWith(getKey(), SignatureAlgorithm.HS512)
                                          .claim("roles", getRoles(authentication));
        return jwtBuilder.compact();
    }

    private Set<String> getRoles(Authentication authentication) {
        return authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    private Key getKey( ) {
        byte[] key = Decoders.BASE64.decode("SECRET_KEY");
        return Keys.hmacShaKeyFor(key);
    }

}
