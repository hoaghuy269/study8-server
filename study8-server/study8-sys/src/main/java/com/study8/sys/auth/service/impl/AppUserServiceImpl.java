package com.study8.sys.auth.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.dto.AppRoleDto;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.entity.AppRole;
import com.study8.sys.auth.entity.AppUser;
import com.study8.sys.auth.enumf.AccountActiveEnum;
import com.study8.sys.auth.enumf.RoleEnum;
import com.study8.sys.auth.enumf.SendOTPEnum;
import com.study8.sys.auth.repository.AppUserRepository;
import com.study8.sys.auth.req.RegisterReq;
import com.study8.sys.auth.service.AppRoleService;
import com.study8.sys.auth.service.AppUserService;
import com.study8.sys.auth.validator.AppUserValidator;
import com.study8.sys.config.SettingVariable;
import com.study8.sys.system.constant.SystemExceptionConstant;
import com.study8.sys.util.ExceptionUtils;
import com.study8.sys.util.UUIDUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
}
