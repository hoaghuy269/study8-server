package com.study8.sys.system.validator;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.dto.AppUserDto;
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

    public Boolean validateBeforeSendOTP(AppUserDto appUserDto, Locale locale)
            throws CoreApplicationException {
        LocalDateTime currentDate = LocalDateTime.now();
        if (ObjectUtils.isEmpty(appUserDto)) { //Validate data
            ExceptionUtils.throwCoreApplicationException(
                    ExceptionConstant.EXCEPTION_DATA_PROCESSING, locale);
        }
        SystemOTPDto systemOTPDto = systemOTPService
                .getByUserId(appUserDto.getId());
        if (ObjectUtils.isNotEmpty(systemOTPDto)) {
            SystemOTP systemOTPUpdated = null;
            if (systemOTPDto.getExpiryDate() != null
                    && currentDate.isAfter(systemOTPDto.getExpiryDate())) { //Update active otp
                SystemOTP systemOTP = systemOTPService
                        .findEntity(systemOTPDto.getId());
                if (ObjectUtils.isEmpty(systemOTP)) {
                    ExceptionUtils.throwCoreApplicationException(
                            ExceptionConstant.EXCEPTION_DATA_PROCESSING, locale);
                }
                systemOTP.setActive(false);
                systemOTP.setDeleted(1);
                systemOTP.setDeletedDate(currentDate);
                systemOTP.setDeletedId(UserProfileUtils.getUserId());
                systemOTPUpdated = systemOTPService.update(systemOTP);
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
}
