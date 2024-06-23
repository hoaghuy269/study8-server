package com.study8.sys.auth.rest.v1.impl;

import com.study8.core.res.CoreApiRes;
import com.study8.core.util.CoreExceptionUtils;
import com.study8.core.util.CoreLanguageUtils;
import com.study8.sys.auth.req.LoginReq;
import com.study8.sys.auth.res.LoginRes;
import com.study8.sys.auth.rest.v1.AuthRest;
import com.study8.sys.constant.MessageKeyConstant;
import com.study8.sys.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * AuthRestImpl
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: Auth Rest Impl
 */
@RestController
@Slf4j
public class AuthRestImpl implements AuthRest {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public CoreApiRes<LoginRes> login(LoginReq loginReq,
            BindingResult bindingResult, HttpServletRequest request,
            HttpServletResponse response) {
        Locale locale = CoreLanguageUtils.getLanguageFromHeader(request);
        try {
            LoginRes res = new LoginRes();
            if (bindingResult.hasErrors()) {
                CoreExceptionUtils.throwCoreApplicationException(
                        MessageKeyConstant.DATA_PROCESSING_ERROR, locale);
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginReq.getUsername(), loginReq.getPassword()));
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            if (StringUtils.isNotEmpty(jwt)) {
                res.setToken(jwt);
            }
            return CoreApiRes.handleSuccess(res, locale);
        } catch (Exception e) {
            log.error("AuthRestImpl | login", e);
            return CoreApiRes.handleError(e.getMessage());
        }
    }
}
