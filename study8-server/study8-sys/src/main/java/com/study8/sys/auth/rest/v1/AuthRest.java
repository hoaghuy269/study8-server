package com.study8.sys.auth.rest.v1;

import com.study8.core.res.CoreApiRes;
import com.study8.sys.auth.constant.AuthApiConstant;
import com.study8.sys.auth.req.ForgotPasswordReq;
import com.study8.sys.auth.req.LoginReq;
import com.study8.sys.auth.req.RegisterReq;
import com.study8.sys.auth.res.ForgotPasswordRes;
import com.study8.sys.auth.res.LoginRes;
import com.study8.sys.auth.res.RegisterRes;
import com.study8.sys.constant.ApiConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * AuthRest
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: API Authentication
 */
@RequestMapping(ApiConstant.API_SYS + ApiConstant.API_V1 + AuthApiConstant.API_AUTH)
public interface AuthRest {
    /**
     * @API: /sys/api/v1/auth/login
     * @Date: 2024-06-11
     * @Author: HuyNH
     * @Desc: Login API
     */
    @PostMapping(AuthApiConstant.API_LOGIN)
    CoreApiRes<LoginRes> login(@RequestBody @Valid LoginReq loginReq,
            BindingResult bindingResult, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * @API: /sys/api/v1/auth/register
     * @Date: 2024-06-27
     * @Author: HuyNH
     * @Desc: Register API
     */
    @PostMapping(AuthApiConstant.API_REGISTER)
    CoreApiRes<RegisterRes> register(@RequestBody @Valid RegisterReq registerReq,
                                     BindingResult bindingResult, HttpServletRequest request,
                                     HttpServletResponse response);

    /**
     * @API: /sys/api/v1/auth/forgot-password
     * @Date: 2024-09-30
     * @Author: HuyNH
     * @Desc: Forgot Password API
     */
    @PostMapping(AuthApiConstant.API_FORGOT_PASSWORD)
    CoreApiRes<ForgotPasswordRes> forgotPassword(@RequestBody @Valid ForgotPasswordReq forgotPasswordReq,
                                                 BindingResult bindingResult, HttpServletRequest request,
                                                 HttpServletResponse response);
}
