package com.study8.sys.auth.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * AuthExceptionConstant
 * @Date: 2024-06-28
 * @Author: HuyNH
 * @Desc: Constant exception for auth
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthExceptionConstant {
    public static final String EXCEPTION_AUTH_ACCOUNT_EXITS = "EXCEPTION_AUTH_ACCOUNT_EXITS";
    public static final String EXCEPTION_AUTH_ACCOUNT_NOT_VALID = "EXCEPTION_AUTH_ACCOUNT_NOT_VALID";
    public static final String EXCEPTION_AUTH_PHONE_NUMBER_EXITS = "EXCEPTION_AUTH_PHONE_NUMBER_EXITS";
}
