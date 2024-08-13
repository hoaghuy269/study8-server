package com.study8.sys.system.rest.v1;

import com.study8.core.res.CoreApiRes;
import com.study8.sys.auth.req.SendOTPReq;
import com.study8.sys.auth.res.SendOTPRes;
import com.study8.sys.constant.ApiConstant;
import com.study8.sys.system.constant.SystemApiConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SystemRest
 * @Date: 2024-08-18
 * @Author: HuyNH
 * @Desc: API System
 */
@RequestMapping(ApiConstant.API_SYS + ApiConstant.API_V1 + SystemApiConstant.API_SYSTEM)
public interface SystemRest {
    /**
     * @API: /sys/api/v1/system/send-otp
     * @Date: 2024-06-27
     * @Author: HuyNH
     * @Desc: Send OTP API
     */
    @PostMapping(SystemApiConstant.API_SEND_OTP)
    CoreApiRes<SendOTPRes> sendOTP(@RequestBody @Valid SendOTPReq sendOTPReq,
                                   BindingResult bindingResult, HttpServletRequest request,
                                   HttpServletResponse response);

    /**
     * @API: /sys/api/v1/system/verify-otp
     * @Date: 2024-08-07
     * @Author: HuyNH
     * @Desc: Verify OTP API
     */
    @GetMapping(SystemApiConstant.API_VERIFY_OTP)
    CoreApiRes<SendOTPRes> verifyOTP(@RequestParam(name = "username") String username,
                                     @RequestParam(name = "code") String code,
                                     HttpServletRequest request,
                                     HttpServletResponse response);
}
