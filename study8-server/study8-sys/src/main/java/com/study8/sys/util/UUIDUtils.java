package com.study8.sys.util;

import com.study8.sys.constant.DateTimeConstant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * UUIDUtils
 * @Date: 2024-06-28
 * @Author: HuyNH
 * @Desc: UUID Utils
 */
public class UUIDUtils {
    public static String randomUUIDByDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeConstant.DDMMYYYY);
        String currentDate = dateFormat.format(new Date());
        String uuid = UUID.randomUUID().toString();
        return currentDate + "-" + uuid;
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }
}
