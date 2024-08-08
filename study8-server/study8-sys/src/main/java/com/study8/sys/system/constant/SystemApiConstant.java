package com.study8.sys.system.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * SystemApiConstant
 * @Date: 2024-08-08
 * @Author: HuyNH
 * @Desc: Constant System API
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SystemApiConstant {
    public static final String API_SYSTEM = "/system";
    public static final String API_SEND_OTP = "/send-otp";
    public static final String API_VERIFY_OTP = "/verify-otp";
}
