package com.study8.sys.system.validator;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.enumf.AccountActiveEnum;
import com.study8.sys.constant.ExceptionConstant;
import com.study8.sys.system.constant.SystemExceptionConstant;
import com.study8.sys.system.dto.SystemOTPDto;
import com.study8.sys.system.entity.SystemOTP;
import com.study8.sys.system.service.SystemOTPService;
import com.study8.sys.util.ExceptionUtils;
import com.study8.sys.util.UserProfileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    public boolean validateBeforeSendOTP(AppUserDto appUserDto, Locale locale)
            throws CoreApplicationException {
        LocalDateTime currentDate = LocalDateTime.now();
        this.validateAccount(appUserDto, locale, true);
        SystemOTPDto systemOTPDto = systemOTPService
                .getByUserId(appUserDto.getId());
        if (ObjectUtils.isNotEmpty(systemOTPDto)) {
            SystemOTP systemOTPUpdated = null;
            if (systemOTPDto.getExpiryDate() != null
                    && currentDate.isAfter(systemOTPDto.getExpiryDate())) { //Update active otp
                systemOTPUpdated = systemOTPService.updateActive(
                        systemOTPDto.getId(), false);
            }
            if (ObjectUtils.isEmpty(systemOTPUpdated)
                    && (systemOTPDto.getActive() != null
                        && systemOTPDto.getActive())) {
                String[] errors = new String[] {String.valueOf(
                        ChronoUnit.MINUTES.between(
                                currentDate,
                                systemOTPDto.getExpiryDate()))};
                ExceptionUtils.throwCoreApplicationException(
                        SystemExceptionConstant.EXCEPTION_OTP_STILL_VALID,
                        locale, errors);
            }
        }
        return true;
    }

    public boolean validateBeforeVerifyOTP(AppUserDto appUserDto, SystemOTPDto systemOTPDto, Locale locale)
            throws CoreApplicationException {
        if (ObjectUtils.isEmpty(systemOTPDto)) { //Validate data
            ExceptionUtils.throwCoreApplicationException(
                    SystemExceptionConstant.EXCEPTION_OTP_NOT_VALID, locale);
        }
        this.validateAccount(appUserDto, locale, false); //Validate active account
        return true;
    }

    private void validateAccount(AppUserDto appUserDto, Locale locale, boolean isHasToken)
            throws CoreApplicationException {
        if (ObjectUtils.isEmpty(appUserDto)) { //Validate appUserDto
            ExceptionUtils.throwCoreApplicationException(
                    ExceptionConstant.EXCEPTION_DATA_PROCESSING, locale);
        }
        if ((UserProfileUtils.getUserId() != null
                && !UserProfileUtils.getUserId().equals(appUserDto.getId()))
                && isHasToken) {
            ExceptionUtils.throwCoreApplicationException(
                    ExceptionConstant.EXCEPTION_ACCOUNT_DOES_NOT_HAVE_PERMISSION, locale);
        }
        if (appUserDto.getActive() != null
                && AccountActiveEnum.ACTIVE.getValue()
                    == appUserDto.getActive()) {
            ExceptionUtils.throwCoreApplicationException(
                    SystemExceptionConstant.EXCEPTION_ACCOUNT_HAS_BEEN_VERIFIED, locale);
        }
    }
}
