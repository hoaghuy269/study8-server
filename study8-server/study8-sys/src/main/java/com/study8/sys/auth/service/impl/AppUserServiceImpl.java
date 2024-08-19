package com.study8.sys.auth.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.entity.AppUser;
import com.study8.sys.auth.enumf.AccountActiveEnum;
import com.study8.sys.auth.repository.AppUserRepository;
import com.study8.sys.auth.req.RegisterReq;
import com.study8.sys.auth.service.AppUserService;
import com.study8.sys.auth.validator.AppUserValidator;
import com.study8.sys.config.SettingVariable;
import com.study8.sys.util.UUIDUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

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

    @Override
    public AppUserDto getByUsername(String username) {
        return appUserRepository.findByUsername(username);
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
            appUserInsert.setCreatedId(SettingVariable.SYSTEM_ADMIN_ID);
            AppUser appUser = appUserRepository.save(appUserInsert);
            if (ObjectUtils.isNotEmpty(appUser)) {
                return objectMapper.convertValue(
                        appUser, AppUserDto.class);
            }
        }
        return null;
    }

    @Override
    public AppUserDto getByPhoneNumber(String phoneNumber) {
        return appUserRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public AppUser activeAccount(Long userId) {
        Optional<AppUser> appUserOptional = appUserRepository
                .findById(userId);
        if (appUserOptional.isPresent()) {
            AppUser appUser = appUserOptional.get();
            appUser.setActive(AccountActiveEnum.ACTIVE.getValue());
            return appUserRepository.save(appUser);
        }
        return null;
    }

    @Override
    public void updateAccount(AppUserDto appUserDto, boolean isUpdateInNewThread) {
        AppUser appUser = objectMapper.convertValue(appUserDto, AppUser.class);
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
}
