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
}
