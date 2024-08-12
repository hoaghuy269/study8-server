package com.study8.sys.util;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.constant.SysConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;
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
    public static void throwCoreApplicationException(String messageKey, Locale locale)
            throws CoreApplicationException {
        ResourceBundle messages = ResourceBundle.getBundle(SysConstant.MESSAGES_SYS, locale);
        String message = messages.getString(messageKey);
        throw new CoreApplicationException(message);
    }

    public static void throwCoreApplicationException(String messageKey, Locale locale, Object[] parameters)
            throws CoreApplicationException {
        ResourceBundle messages = ResourceBundle.getBundle(SysConstant.MESSAGES_SYS, locale);
        String messageTemplate = messages.getString(messageKey);
        String formattedMessage = MessageFormat.format(messageTemplate, parameters);
        throw new CoreApplicationException(formattedMessage);
    }
}
