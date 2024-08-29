package com.study8.sys.util;

import com.study8.sys.constant.SysConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ResourceBundleUtils
 * @Date: 2024-07-08
 * @Author: HuyNH
 * @Desc: Resource Bundle Utils
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceUtils {
    public static String getMessage(String messageKey, Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle(
                SysConstant.MESSAGES_SYS, locale);
        return messages.getString(messageKey);
    }

    public static String getStringResource(String resourcePath) {
        ClassPathResource resource = new ClassPathResource(resourcePath);
        try {
            return StreamUtils.copyToString(
                    resource.getInputStream(),
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("ResourceUtils | getStringResource", e);
            return null;
        }
    }
}
