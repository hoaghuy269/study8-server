package com.study8.sys.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * SettingVariable
 * @Date: 2024-07-09
 * @Author: HuyNH
 * @Desc: Setting Variable
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SettingVariable {
    public static final Long RATE_LIMIT = 10L;
    public static final int PRIORITY_1 = 1;
    public static final Long SYSTEM_ADMIN_ID = 1L;
}
