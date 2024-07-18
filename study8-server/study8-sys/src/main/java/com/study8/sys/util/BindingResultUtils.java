package com.study8.sys.util;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.constant.ExceptionConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.Locale;

/**
 * Class: BindingResultUtils
 * @Date: 2024-07-18
 * @Author: HuyNH
 * @Desc: Binding Result Utils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BindingResultUtils {
    public static void handleBindingResult(BindingResult bindingResult, Locale locale) throws CoreApplicationException {
        if (bindingResult.hasErrors()) {
            ExceptionUtils.throwCoreApplicationException(
                    ExceptionConstant.EXCEPTION_DATA_PROCESSING, locale);
        }
    }
}
