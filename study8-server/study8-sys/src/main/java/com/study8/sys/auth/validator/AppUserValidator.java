package com.study8.sys.auth.validator;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.constant.AuthExceptionConstant;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.service.AppUserService;
import com.study8.sys.util.ExceptionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * AppUserValidator
 * @Date: 2024-06-28
 * @Author: HuyNH
 * @Desc: AppUser Validator
 */
@Component
public class AppUserValidator {
    @Lazy
    @Autowired
    private AppUserService appUserService;

    public boolean isAccountNotExits(String username, String phoneNumber, Locale locale)
            throws CoreApplicationException {
        AppUserDto appUserDto = appUserService.getByUsername(username);
        if (ObjectUtils.isNotEmpty(appUserDto)) {
            ExceptionUtils.throwCoreApplicationException(
                    AuthExceptionConstant.EXCEPTION_AUTH_ACCOUNT_EXITS, locale);
        }
        appUserDto = appUserService.getByPhoneNumber(phoneNumber);
        if (ObjectUtils.isNotEmpty(appUserDto)) {
            ExceptionUtils.throwCoreApplicationException(
                    AuthExceptionConstant.EXCEPTION_AUTH_PHONE_NUMBER_EXITS, locale);
        }
        return true;
    }
}
