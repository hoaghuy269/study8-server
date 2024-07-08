package com.study8.sys.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ResourceBundleUtils
 * @Date: 2024-07-08
 * @Author: HuyNH
 * @Desc: Resource Bundle Utils
 */
public class ResourceBundleUtils {
    private static final String MESSAGES = "messages-sys";

    public static String getMessage(String messageKey, Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle(MESSAGES, locale);
        return messages.getString(messageKey);
    }
}
