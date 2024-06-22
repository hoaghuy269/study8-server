package com.study8.sys.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study8.core.res.CoreApiRes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthEntryPointJwt
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: Auth Entry Point Jwt
 */
@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        log.error("AuthEntryPointJwt | Unauthorized error", authException);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        CoreApiRes<Void> body = CoreApiRes.handleAuthError(authException.getMessage());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}