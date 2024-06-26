package com.study8.sys.util;

import com.study8.core.exception.CoreApplicationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ExceptionUtils
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: Throw exception utils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionUtils {
    private static final String MESSAGES = "messages-sys";

    public static void throwCoreApplicationException(String messageKey, Locale locale) throws
            CoreApplicationException {
        ResourceBundle messages = ResourceBundle.getBundle(MESSAGES, locale);
        String message = messages.getString(messageKey);
        throw new CoreApplicationException(message);
    }
}
