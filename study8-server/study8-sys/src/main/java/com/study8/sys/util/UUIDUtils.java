package com.study8.sys.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * UUIDUtils
 * @Date: 2024-06-28
 * @Author: HuyNH
 * @Desc: UUID Utils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UUIDUtils {
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }
}
