package com.study8.sys.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateTimeUtils
 * @Date: 2024-10-14
 * @Author: HuyNH
 * @Desc: DateTimeUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtils {
    public static String getDateString(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }
}
