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
import com.study8.sys.util.UUIDUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

/**
 * AppUserServiceImpl
 * @Date: 2024-06-01
 * @Author: HuyNH
 * @Desc: AppUser Service Impl
 */
@Service
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AppUserValidator appUserValidator;

    @Override
    public AppUserDto getByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public AppUserDto register(RegisterReq registerReq, Locale locale) throws CoreApplicationException {
        AppUserDto result = new AppUserDto();
        LocalDateTime today = LocalDateTime.now();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //Validate exits
        Boolean isAccountValid = appUserValidator.isAccountNotExits(
                registerReq.getUsername(), registerReq.getPhoneNumber(), locale);
        if (Boolean.TRUE.equals(isAccountValid)) { //Do save account
            AppUser appUserInsert = new AppUser();
            appUserInsert.setCode(UUIDUtils.randomUUID());
            appUserInsert.setUsername(registerReq.getUsername());
            appUserInsert.setPassword(passwordEncoder.encode(registerReq.getPassword()));
            appUserInsert.setActive(AccountActiveEnum.INACTIVE.getValue());
            appUserInsert.setCreatedDate(today);
            appUserInsert.setPhoneNumber(registerReq.getPhoneNumber());
            AppUser appUser = appUserRepository.save(appUserInsert);
            if (ObjectUtils.isNotEmpty(appUser)) {
                result = objectMapper.convertValue(appUser, AppUserDto.class);
            }
        }
        return result;
    }

    @Override
    public AppUserDto getByPhoneNumber(String phoneNumber) {
        return appUserRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Boolean isAccountExits(String username, String phoneNumber) {
        return appUserValidator.isAccountExits(
                username, phoneNumber);
    }
}
