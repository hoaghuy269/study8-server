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
    public static final String FULL_NAME_PATTERN = "^[a-zA-Z0-9 ]*$";
    public static final String PASSWORD_PATTERN = "^[^\\s]*$";
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public static final String TEL_PATTERN = "^\\+?[0-9. ()-]{7,25}$";
}
