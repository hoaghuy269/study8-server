package com.study8.sys.auth.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.constant.AuthApiConstant;
import com.study8.sys.auth.constant.AuthExceptionConstant;
import com.study8.sys.auth.dto.AppRoleDto;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.entity.AppRole;
import com.study8.sys.auth.entity.AppUser;
import com.study8.sys.auth.enumf.AccountActiveEnum;
import com.study8.sys.auth.enumf.RoleEnum;
import com.study8.sys.auth.enumf.SendOTPEnum;
import com.study8.sys.auth.repository.AppUserRepository;
import com.study8.sys.auth.req.ForgotPasswordReq;
import com.study8.sys.auth.req.RegisterReq;
import com.study8.sys.auth.res.ForgotPasswordRes;
import com.study8.sys.auth.res.ResetPasswordRes;
import com.study8.sys.auth.service.AppRoleService;
import com.study8.sys.auth.service.AppUserService;
import com.study8.sys.auth.validator.AppUserValidator;
import com.study8.sys.config.SettingVariable;
import com.study8.sys.constant.ApiConstant;
import com.study8.sys.constant.DateTimeConstant;
import com.study8.sys.constant.ExceptionConstant;
import com.study8.sys.constant.SignConstant;
import com.study8.sys.constant.SysConstant;
import com.study8.sys.system.constant.SystemExceptionConstant;
import com.study8.sys.system.dto.SendEmailDto;
import com.study8.sys.system.dto.SendEmailResultDto;
import com.study8.sys.system.dto.SystemOTPDto;
import com.study8.sys.system.entity.SystemOTP;
import com.study8.sys.system.enumf.EmailEnum;
import com.study8.sys.system.enumf.SystemErrorEnum;
import com.study8.sys.system.enumf.SystemErrorPriorityEnum;
import com.study8.sys.system.service.EmailService;
import com.study8.sys.system.service.SystemErrorLogService;
import com.study8.sys.system.service.SystemOTPService;
import com.study8.sys.util.DateTimeUtils;
import com.study8.sys.util.ExceptionUtils;
import com.study8.sys.util.ResourceUtils;
import com.study8.sys.util.UUIDUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * AppUserServiceImpl
 * @Date: 2024-06-01
 * @Author: HuyNH
 * @Desc: AppUser Service Impl
 */
@Service
@Slf4j
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AppUserValidator appUserValidator;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private AppRoleService appRoleService;

    @Lazy
    @Autowired
    private SystemErrorLogService systemErrorLogService;

    @Lazy
    @Autowired
    private SystemOTPService systemOTPService;

    @Autowired
    private EmailService emailService;

    @Override
    public AppUserDto getByUsername(String username) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (ObjectUtils.isNotEmpty(appUser)) {
            return objectMapper.convertValue(
                    appUser, AppUserDto.class);
        }
        return null;
    }

    @Override
    public AppUserDto register(RegisterReq registerReq, Locale locale) throws CoreApplicationException {
        LocalDateTime today = LocalDateTime.now();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (appUserValidator.validateBeforeRegister(registerReq.getUsername(),
                registerReq.getPhoneNumber(), locale)) { //Do save account
            AppUser appUserInsert = new AppUser();
            appUserInsert.setCode(UUIDUtils.randomUUID());
            appUserInsert.setUsername(registerReq.getUsername());
            appUserInsert.setPassword(passwordEncoder.encode(registerReq.getPassword()));
            appUserInsert.setActive(AccountActiveEnum.INACTIVE.getValue());
            appUserInsert.setCreatedDate(today);
            appUserInsert.setPhoneNumber(registerReq.getPhoneNumber());
            appUserInsert.setEmailVerified(false);
            appUserInsert.setPhoneNumberVerified(false);
            appUserInsert.setCreatedId(SettingVariable.SYSTEM_ADMIN_ID);

            //ROLE_VISITOR
            Set<AppRole> appRoleSet = new HashSet<>();
            AppRoleDto roleVisitorDto = appRoleService
                    .getByName(RoleEnum.ROLE_VISITOR
                            .getValue());
            if (ObjectUtils.isEmpty(roleVisitorDto)) {
                ExceptionUtils.throwCoreApplicationException(
                        SystemExceptionConstant.EXCEPTION_OTP_NOT_VALID, locale);
            }
            appRoleSet.add(objectMapper
                    .convertValue(roleVisitorDto,
                            AppRole.class));
            appUserInsert.setRoles(appRoleSet);

            //Do save data
            AppUser appUser = appUserRepository.save(appUserInsert);
            if (ObjectUtils.isNotEmpty(appUser)) {
                return objectMapper.convertValue(
                        appUser, AppUserDto.class);
            }
        }
        return null;
    }

    @Override
    public List<AppUserDto> getListByPhoneNumber(String phoneNumber) {
        List<AppUser> appUserList = appUserRepository
                .findByPhoneNumber(phoneNumber);
        if (CollectionUtils.isNotEmpty(appUserList)) {
            return appUserList.stream()
                    .map(appUser -> objectMapper.convertValue(appUser, AppUserDto.class))
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public AppUser activeAccount(Long userId, Integer activeType) {
        Optional<AppUser> appUserOptional = appUserRepository
                .findById(userId);
        if (appUserOptional.isPresent()) {
            AppUser appUser = appUserOptional.get();
            appUser.setActive(AccountActiveEnum.ACTIVE.getValue());
            SendOTPEnum sendOTPEnum = SendOTPEnum.resolveByValue(activeType);
            switch (sendOTPEnum) {
                case EMAIL -> appUser.setEmailVerified(true);
                case PHONE_NUMBER -> appUser.setPhoneNumberVerified(true);
                case UNKNOWN -> {
                    return null;
                }
            }
            return appUserRepository.save(appUser);
        }
        return null;
    }

    @Override
    public void updateAccount(AppUserDto appUserDto, boolean isUpdateInNewThread) {
        AppUser appUser = objectMapper.convertValue(appUserDto, AppUser.class);
        Set<AppRole> appRoleSet = appRoleService
                .getSetByUserId(appUser.getId());
        if (CollectionUtils.isNotEmpty(appRoleSet)) {
            appUser.setRoles(appRoleSet);
        }
        if (isUpdateInNewThread) { //New thread
            TransactionTemplate transactionTemplate = new TransactionTemplate(
                    platformTransactionManager);
            Runnable saveMERequirementDetailHist = () -> transactionTemplate
                    .execute(new TransactionCallbackWithoutResult() {
                        @Override
                        protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {
                            appUserRepository.save(appUser);
                        }
                    });
            Thread thread = new Thread(saveMERequirementDetailHist);
            thread.start();
        } else { //In thread
            appUserRepository.save(appUser);
        }
    }

    @Override
    public boolean isEmailVerified(String email) {
        return appUserValidator
                .validateEmailVerified(email);
    }

    @Override
    public AppUserDto getById(Long id) {
        Optional<AppUser> appUser = appUserRepository.findById(id);
        if (appUser.isPresent()) {
            return objectMapper.convertValue(
                    appUser,
                    AppUserDto.class);
        }
        return null;
    }

    @Override
    public List<AppUserDto> getListByEmail(String email) {
        List<AppUser> appUserList = appUserRepository
                .findByEmail(email);
        if (CollectionUtils.isNotEmpty(appUserList)) {
            return appUserList.stream()
                    .map(appUser -> objectMapper.convertValue(appUser, AppUserDto.class))
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public ForgotPasswordRes forgotPassword(ForgotPasswordReq forgotPasswordReq, Locale locale)
            throws CoreApplicationException {
        ForgotPasswordRes result = new ForgotPasswordRes();
        result.setIsAccountValid(false);
        result.setIsSendOTP(false);

        AppUserDto appUserDto = null;
        if (appUserValidator.validateBeforeForgotPassword(
                forgotPasswordReq,
                locale)) {
            appUserDto = this.getAppUserForgotPassword(
                    forgotPasswordReq, locale);
        }

        if (ObjectUtils.isNotEmpty(appUserDto)) {
            result.setIsAccountValid(true);

            //Send OTP
            SendOTPEnum sendOTPEnum = SendOTPEnum.resolveByValue(forgotPasswordReq.getOtpType());
            boolean isSendSuccess = false;
            switch (sendOTPEnum) {
                case PHONE_NUMBER -> isSendSuccess = this.sendSMSForgotPassword();
                case EMAIL -> isSendSuccess = this.sendEmailForgotPassword(appUserDto, locale);
                case UNKNOWN -> ExceptionUtils.throwCoreApplicationException(
                        ExceptionConstant.EXCEPTION_DATA_PROCESSING, locale);
            }
            result.setIsSendOTP(isSendSuccess);

            String urlResetPassword = this.getUrlResetPassword(appUserDto);
            result.setUrlResetPassword(urlResetPassword);
        } else {
            ExceptionUtils.throwCoreApplicationException(
                    AuthExceptionConstant.EXCEPTION_ACCOUNT_DOES_NOT_EXIST,
                    locale);
        }

        return result;
    }

    @Override
    public ResetPasswordRes resetPassword(String code, String otpCode, String newPassword, Locale locale)
            throws CoreApplicationException {
        ResetPasswordRes result = new ResetPasswordRes();

        AppUser appUser = appUserRepository.findByResetPassword(code);
        AppUserDto appUserDto = objectMapper.convertValue(appUser, AppUserDto.class);

        SystemOTPDto systemOTPDto = null;
        if (ObjectUtils.isNotEmpty(appUser)
                && appUserDto.getId()!= null) {
            systemOTPDto = systemOTPService.getByCode(code, appUserDto.getId());
        }

        if (appUserValidator.validateBeforeResetPassword(
                appUserDto,
                systemOTPDto,
                locale)) {

        }

        //Reset password
        String passwordEncode = new BCryptPasswordEncoder()
                .encode(newPassword);

        appUser.setPassword(passwordEncode);
        appUser.setResetPassword(null);
        appUser.setUpdatedId(SettingVariable.SYSTEM_ADMIN_ID);
        appUser.setUpdatedDate(LocalDateTime.now());


        this.updateAccount(appUserDto, false);

        result.setSuccess(true);
        return result;
    }

    private AppUserDto getAppUserForgotPassword(ForgotPasswordReq forgotPasswordReq, Locale locale)
            throws CoreApplicationException {
        AppUserDto result = null;
        //username
        if (StringUtils.isNotEmpty(forgotPasswordReq.getUsername())) {
            result = this.getByUsername(
                    forgotPasswordReq.getUsername());
        }

        //phone number
        if (StringUtils.isNotEmpty(forgotPasswordReq.getPhoneNumber())) {
            List<AppUserDto> appUserDtoList = this
                    .getListByPhoneNumber(forgotPasswordReq.getEmail());
            result = appUserDtoList.stream()
                    .filter(AppUserDto::getPhoneNumberVerified)
                    .findFirst()
                    .orElse(this.saveErrorLog(locale));
        }

        //email
        if (StringUtils.isNotEmpty(
                forgotPasswordReq.getEmail())) {
            List<AppUserDto> appUserDtoList = this
                    .getListByEmail(forgotPasswordReq.getEmail());
            result = appUserDtoList.stream()
                    .filter(AppUserDto::getEmailVerified)
                    .findFirst()
                    .orElse(this.saveErrorLog(locale));
        }
        return result;
    }


    private AppUserDto saveErrorLog(Locale locale)
            throws CoreApplicationException {
        systemErrorLogService.saveErrorLog(
                SystemErrorEnum.ERROR_DB_1001,
                SystemErrorPriorityEnum.CRITICAL,
                locale,
                true);
        ExceptionUtils.throwCoreApplicationException(
                ExceptionConstant.EXCEPTION_SYSTEM_ERROR,
                locale);
        return null;
    }

    private boolean sendSMSForgotPassword() {
        //TODO:Send SMS
        return false;
    }

    private boolean sendEmailForgotPassword(AppUserDto appUserDto, Locale locale) {
        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setSubject(ResourceUtils
                .getMessage(SysConstant.EMAIL_003_SUBJECT,
                        locale));
        sendEmailDto.setTemplateCode(EmailEnum.FORGOT_PASSWORD_EMAIL.toString());
        sendEmailDto.setTo(List.of(appUserDto.getEmail()));

        int otpInt = new SecureRandom().nextInt(1000000);
        String otpCode = String.format("%06d", otpInt);

        SystemOTP systemOTP = systemOTPService
                .generateOTP(SendOTPEnum.EMAIL, otpCode, appUserDto.getId());

        Map<String, Object> mapData = new HashMap<>(); //Data
        mapData.put("username", appUserDto.getUsername());
        mapData.put("otpCode", systemOTP.getOtpCode());
        mapData.put("expiredDate", DateTimeUtils.getDateString(systemOTP.getExpiryDate(),
                DateTimeConstant.DATETIME_NO_SECOND));
        sendEmailDto.setMapData(mapData);

        //Do send email
        SendEmailResultDto sendEmailResultDto = emailService.sendEmailSMTP(sendEmailDto, locale);
        return sendEmailResultDto.getIsSuccess();
    }

    private String getUrlResetPassword(AppUserDto appUserDto) {
        String code = UUIDUtils.randomUUID();

        //Update account
        appUserDto.setResetPassword(code);
        this.updateAccount(appUserDto, false);

        return ApiConstant.API_SYS
                + ApiConstant.API_V1
                + AuthApiConstant.API_AUTH
                + AuthApiConstant.API_RESET_PASSWORD
                + SignConstant.SPLASH
                + code;
    }
}
