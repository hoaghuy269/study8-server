package com.study8.sys.util;

import com.study8.sys.constant.SysConstant;
import com.study8.sys.service.UserDetailsImpl;
import com.study8.sys.system.service.SystemConfigService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Class: JwtUtils
 * @Date: 2024-05-11
 * @Author: HuyNH
 * @Desc: Generate token
 */
@Component
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private SystemConfigService systemConfigService;

    private int getJwtExpiration() {
        return systemConfigService
                .getIntValue(SysConstant.JWT_EXPIRATION,
                        SysConstant.SYSTEM);
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        SecretKey secretKey = this.getSecretKey();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()
                        + this.getJwtExpiration()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        SecretKey secretKey = this.getSecretKey();
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        SecretKey secretKey = this.getSecretKey();
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("JwtUtils | Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.error("JwtUtils | JWT token is expired", e);
        } catch (UnsupportedJwtException e) {
            log.error("JwtUtils | JWT token is unsupported", e);
        } catch (IllegalArgumentException e) {
            log.error("JwtUtils | JWT claims string is empty", e);
        }
        return false;
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret
                .getBytes(StandardCharsets
                        .UTF_8));
    }
}
