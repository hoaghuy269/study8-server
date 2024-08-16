package com.study8.gateway.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * SysServiceConstant
 * @Date: 2024-08-14
 * @Author: HuyNH
 * @Desc: Sys-Service Constant
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SysServiceConstant {
    public static final String SYS_AUTH_ROUTE_ID = "study8-sys-auth";
    public static final String SYS_AUTH_PATH = "/sys/api/v1/auth/**";
    public static final String SYS_SYSTEM_ROUTE_ID = "study8-sys-system";
    public static final String SYS_SYSTEM_PATH = "/sys/api/v1/system/**";
    public static final String SYS_AUTH_API_LOGIN = "/sys/api/v1/auth/login";
}
