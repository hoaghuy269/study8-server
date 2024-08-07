package com.study8.sys.system.validator;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.constant.ExceptionConstant;
import com.study8.sys.system.constant.SystemExceptionConstant;
import com.study8.sys.system.dto.SystemOTPDto;
import com.study8.sys.system.service.SystemOTPService;
import com.study8.sys.util.ExceptionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * SystemOTPValidator
 * @Date: 2024-06-28
 * @Author: HuyNH
 * @Desc: SystemOTP Validator
 */
@Component
public class SystemOTPValidator {
    @Lazy
    @Autowired
    private SystemOTPService systemOTPService;

    public Boolean validateBeforeSendOTP(AppUserDto appUserDto, Locale locale)
            throws CoreApplicationException {
        if (ObjectUtils.isNotEmpty(appUserDto)) {
            ExceptionUtils.throwCoreApplicationException(
                    ExceptionConstant.EXCEPTION_DATA_PROCESSING, locale);
        }
        SystemOTPDto systemOTPDto = systemOTPService
                .getByUserId(appUserDto.getId());
        if (ObjectUtils.isEmpty(systemOTPDto)) {
            //TODO: Validate theo phút chưa thực hiện
            ExceptionUtils.throwCoreApplicationException(
                    SystemExceptionConstant.EXCEPTION_OTP_STILL_VALID, locale);
        }
        return true;
    }
}
