package com.study8.sys.auth.rest.v1;

import com.study8.core.res.CoreApiRes;
import com.study8.sys.auth.constant.AuthApiConstant;
import com.study8.sys.auth.req.LoginReq;
import com.study8.sys.auth.res.LoginRes;
import com.study8.sys.constant.ApiConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
     * @Desc: Login api
     */
    @PostMapping(AuthApiConstant.API_LOGIN)
    CoreApiRes<LoginRes> login(@RequestBody LoginReq loginReq,
                                           HttpServletRequest request,
                                           HttpServletResponse response);
}
