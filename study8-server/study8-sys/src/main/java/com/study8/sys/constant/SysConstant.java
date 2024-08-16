package com.study8.sys.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * SysConstant
 * @Date: 2024-06-28
 * @Author: HuyNH
 * @Desc: Sys constant
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SysConstant {
    public static final String PASSWORD_PATTERN = "^[^\\s]*$";
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public static final String PHONE_NUMBER_PATTERN = "^\\+?[0-9. ()-]{7,25}$";
    public static final String EMAIL = "EMAIL";
    public static final String EMAIL_USERNAME = "EMAIL_USERNAME";
    public static final String EMAIL_PASSWORD = "EMAIL_PASSWORD";
    public static final String MESSAGES_SYS = "messages_sys";
    public static final String RESOURCE_EMAIL_TEMPLATES = "email-templates";
    public static final String EMAIL_001_SUBJECT = "EMAIL_001_SUBJECT";
    public static final String OTP_EXPIRATION = "OTP_EXPIRATION";
    public static final String SYSTEM = "SYSTEM";
    public static final String JWT_EXPIRATION = "JWT_EXPIRATION";
    public static final String ROOT_DOMAIN = "ROOT_DOMAIN";
    public static final String STUDY8_DOMAIN = "STUDY8_DOMAIN";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String JWT_SECRET ="JWT_SECRET";
}
