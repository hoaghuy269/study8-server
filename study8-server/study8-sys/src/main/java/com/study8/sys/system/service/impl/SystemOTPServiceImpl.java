package com.study8.sys.system.service.impl;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.enumf.SendOTPEnum;
import com.study8.sys.system.req.SendOTPReq;
import com.study8.sys.system.res.SendOTPRes;
import com.study8.sys.auth.service.AppUserService;
import com.study8.sys.constant.ApiConstant;
import com.study8.sys.system.constant.SystemApiConstant;
import com.study8.sys.system.dto.SystemOTPDto;
import com.study8.sys.system.entity.SystemOTP;
import com.study8.sys.system.repository.SystemOTPRepository;
import com.study8.sys.system.res.VerifyOTPRes;
import com.study8.sys.system.service.SystemConfigService;
import com.study8.sys.system.service.SystemOTPService;
import com.study8.sys.constant.ExceptionConstant;
import com.study8.sys.constant.SysConstant;
import com.study8.sys.system.dto.SendEmailDto;
import com.study8.sys.system.dto.SendEmailResultDto;
import com.study8.sys.system.enumf.EmailEnum;
import com.study8.sys.system.service.EmailService;
import com.study8.sys.system.validator.SystemOTPValidator;
import com.study8.sys.util.ExceptionUtils;
import com.study8.sys.util.ResourceUtils;
import com.study8.sys.util.UUIDUtils;
import com.study8.sys.util.UserProfileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * OTPServiceImpl
 * @Date: 2024-07-12
 * @Author: HuyNH
 * @Desc: OTP Service Impl
 */
@Service
public class SystemOTPServiceImpl implements SystemOTPService {
    @Autowired
    private EmailService emailService;

    @Autowired
    private SystemOTPRepository systemOTPRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private SystemOTPValidator systemOTPValidator;

    @Override
    public SendOTPRes sendOTP(SendOTPReq sendOTPReq, Locale locale)
            throws CoreApplicationException {
        SendOTPRes result = new SendOTPRes();
        AppUserDto appUserDto = appUserService
                .getByUsername(sendOTPReq.getUsername());
        if (systemOTPValidator.validateBeforeSendOTP(
                appUserDto, locale)) { //Validate before action
            SendOTPEnum sendOTPEnum = SendOTPEnum.resolveByValue(sendOTPReq.getType());
            switch (sendOTPEnum) {
                case PHONE_NUMBER -> this.sendPhoneNumberOTP();
                case EMAIL -> this.sendEmailOTP(sendOTPReq, appUserDto, locale);
                case UNKNOWN -> ExceptionUtils.throwCoreApplicationException(
                        ExceptionConstant.EXCEPTION_DATA_PROCESSING, locale);
            }
            result.setIsSendOTP(true);
        }
        return result;
    }

    @Override
    public SystemOTPDto getByUserId(Long userId) {
        return systemOTPRepository
                .findByUserId(userId);
    }

    @Override
    public SystemOTP updateActive(Long systemOTPId) {
        LocalDateTime currentDate = LocalDateTime.now();
        Optional<SystemOTP> systemOTPOptional = systemOTPRepository
                .findById(systemOTPId);
        if (systemOTPOptional.isPresent()) {
            SystemOTP systemOTP = systemOTPOptional.get();
            systemOTP.setActive(false);
            systemOTP.setDeleted(1);
            systemOTP.setDeletedDate(currentDate);
            systemOTP.setDeletedId(UserProfileUtils.getUserId());
            return systemOTPRepository.save(systemOTP);
        }
        return null;
    }

    @Override
    public VerifyOTPRes verifyOTP(String username, String code, Locale locale)
            throws CoreApplicationException {
        VerifyOTPRes result = new VerifyOTPRes();
        AppUserDto appUserDto = appUserService
                .getByUsername(username);
        SystemOTPDto systemOTPDto = systemOTPRepository
                .findByOTPCode(code);
        if (systemOTPValidator.validateBeforeVerifyOTP(
                appUserDto, systemOTPDto, locale)) { //Validate
            //TODO: Làm tiếp verify
        }
        return result;
    }

    private void sendEmailOTP(SendOTPReq sendOTPReq, AppUserDto appUserDto, Locale locale)
            throws CoreApplicationException {
        SystemOTP systemOTP = this.generateOTPEmail(appUserDto, locale);
        if (ObjectUtils.isNotEmpty(systemOTP)) {
            SendEmailDto sendEmailDto = new SendEmailDto();
            sendEmailDto.setTo(Collections.singletonList(
                    sendOTPReq.getEmail()));
            sendEmailDto.setTemplateCode(EmailEnum.OTP_EMAIL.toString());
            sendEmailDto.setSubject(ResourceUtils
                    .getMessage(SysConstant.EMAIL_001_SUBJECT,
                            locale));
            Map<String, Object> mapData = new HashMap<>(); //Data
            mapData.put("verifyUrl", this.getVerifyUrl(systemOTP.getOtpCode(),
                    sendOTPReq.getUsername()));
            mapData.put("domain", this.getDomain(locale));
            mapData.put("userName", sendOTPReq.getUsername());
            sendEmailDto.setMapData(mapData);
            //Do send email
            SendEmailResultDto sendEmailResultDto = emailService
                    .sendEmailSMTP(sendEmailDto, locale);
            if (ObjectUtils.isNotEmpty(sendEmailResultDto)
                    && sendEmailResultDto.getIsSuccess()) {
                LocalDateTime sentDate = sendEmailResultDto
                        .getTime();
                Optional<SystemOTP> systemOTPUpdate = systemOTPRepository
                        .findById(systemOTP.getId());
                systemOTPUpdate.ifPresent(otp -> {
                    otp.setSentDate(sentDate);
                    systemOTPRepository.save(otp); //Update
                });
            }
        }
    }

    private void sendPhoneNumberOTP() {
        //TODO: Send OTP to phone
    }

    private SystemOTP generateOTPEmail(AppUserDto appUserDto, Locale locale)
            throws CoreApplicationException {
        LocalDateTime currentDate = LocalDateTime.now();
        Long userId = this.getUserId(appUserDto, locale);

        //Do generate random OTP
        SystemOTP systemOTP = new SystemOTP();
        systemOTP.setUserId(userId);
        systemOTP.setOtpType(SendOTPEnum.EMAIL.getValue());
        systemOTP.setOtpCode(UUIDUtils.randomUUID());
        systemOTP.setActive(true);
        systemOTP.setExpiryDate(this.getOTPExpiryDate(currentDate));
        systemOTP.setCreatedDate(currentDate);
        systemOTP.setCreatedId(UserProfileUtils.getUserId());
        return systemOTPRepository.save(systemOTP);
    }

    private Long getUserId(AppUserDto appUserDto, Locale locale)
            throws CoreApplicationException {
        if (ObjectUtils.isNotEmpty(appUserDto)
                    && appUserDto.getId() == null) {
            ExceptionUtils.throwCoreApplicationException(
                    ExceptionConstant.EXCEPTION_DATA_PROCESSING, locale);
        }
        return appUserDto.getId();
    }

    private LocalDateTime getOTPExpiryDate(LocalDateTime currentDate) {
        long extraTime = systemConfigService
                .getLongValue(SysConstant.OTP_EXPIRATION,
                        SysConstant.SYSTEM);
        return currentDate.plus(Duration
                .ofMillis(extraTime));
    }

    private String getVerifyUrl(String otpCode, String username) {
        String rootDomain = systemConfigService
                .getStringValue(SysConstant.ROOT_DOMAIN, SysConstant.SYSTEM);
        String verifyUrl = rootDomain
                + ApiConstant.API_SYS
                + ApiConstant.API_V1
                + SystemApiConstant.API_SYSTEM
                + SystemApiConstant.API_VERIFY_OTP;
        String usernameParam = "?username=" + username;
        String codeParam = "?code=" + otpCode;
        return verifyUrl
                + usernameParam
                + codeParam;
    }

    private String getDomain(Locale locale)
            throws CoreApplicationException {
        String domain = systemConfigService
                .getStringValue(SysConstant.STUDY8_DOMAIN, SysConstant.SYSTEM);
        if (StringUtils.isEmpty(domain)) {
            ExceptionUtils.throwCoreApplicationException(
                    ExceptionConstant.EXCEPTION_DATA_PROCESSING, locale);
        }
        return domain;
    }
}
