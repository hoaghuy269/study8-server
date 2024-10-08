package com.study8.sys.system.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * SystemExceptionConstant
 * @Date: 2024-06-01
 * @Author: HuyNH
 * @Desc: System Exception Constant
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SystemExceptionConstant {
    public static final String EXCEPTION_OTP_STILL_VALID = "EXCEPTION_OTP_STILL_VALID";
    public static final String EXCEPTION_OTP_NOT_VALID = "EXCEPTION_OTP_NOT_VALID";
    public static final String EXCEPTION_ACCOUNT_HAS_BEEN_VERIFIED = "EXCEPTION_ACCOUNT_HAS_BEEN_VERIFIED";
    public static final String EXCEPTION_EMAIL_HAS_BEEN_VERIFIED = "EXCEPTION_EMAIL_HAS_BEEN_VERIFIED";
}
