package com.study8.sys.auth.validator;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.constant.AuthExceptionConstant;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.repository.AppUserRepository;
import com.study8.sys.util.ExceptionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    AppUserRepository appUserRepository;

    public Boolean isAccountNotExits(String emailOrPhoneNumber, Locale locale) throws CoreApplicationException {
        AppUserDto appUserDto = appUserRepository.findByEmailOrPhoneNumber(emailOrPhoneNumber);
        if (ObjectUtils.isNotEmpty(appUserDto)) {
            ExceptionUtils.throwCoreApplicationException(
                    AuthExceptionConstant.EXCEPTION_AUTH_ACCOUNT_EXITS, locale);
        }
        return true;
    }
}
