package com.study8.sys.auth.validator;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.constant.AuthExceptionConstant;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.service.AppUserService;
import com.study8.sys.util.ExceptionUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
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

    public boolean validateBeforeRegister(String username, String phoneNumber, Locale locale)
            throws CoreApplicationException {
        AppUserDto appUserDto = appUserService.getByUsername(username);
        if (ObjectUtils.isNotEmpty(appUserDto)) {
            ExceptionUtils.throwCoreApplicationException(
                    AuthExceptionConstant.EXCEPTION_AUTH_ACCOUNT_EXITS, locale);
        }
        if (this.validatePhoneNumberVerified(phoneNumber)) {
            ExceptionUtils.throwCoreApplicationException(
                    AuthExceptionConstant.EXCEPTION_AUTH_PHONE_NUMBER_EXITS, locale);
        }
        return true;
    }

    public boolean validateEmailVerified(String email) {
        List<AppUserDto> appUserDtoList = appUserService
                .getListByEmail(email);
        return CollectionUtils.isNotEmpty(appUserDtoList) &&
                appUserDtoList.stream()
                        .anyMatch(AppUserDto::getEmailVerified);
    }

    public boolean validatePhoneNumberVerified(String phoneNumber) {
        List<AppUserDto> appUserDtoList = appUserService
                .getListByPhoneNumber(phoneNumber);
        return CollectionUtils.isNotEmpty(appUserDtoList) &&
                appUserDtoList.stream()
                        .anyMatch(AppUserDto::getPhoneNumberVerified);
    }

}
