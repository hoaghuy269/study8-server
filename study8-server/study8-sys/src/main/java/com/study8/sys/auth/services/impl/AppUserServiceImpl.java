package com.study8.sys.auth.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.constant.AuthExceptionConstant;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.entity.AppUser;
import com.study8.sys.auth.enumf.AccountActiveEnum;
import com.study8.sys.auth.repository.AppUserRepository;
import com.study8.sys.auth.req.RegisterReq;
import com.study8.sys.auth.services.AppUserService;
import com.study8.sys.auth.validator.AppUserValidator;
import com.study8.sys.constant.SysConstant;
import com.study8.sys.util.ExceptionUtils;
import com.study8.sys.util.UUIDUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * AppUserServiceImpl
 * @Date: 2024-06-01
 * @Author: HuyNH
 * @Desc: AppUser Service Impl
 */
@Service
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AppUserValidator appUserValidator;

    @Override
    public AppUserDto getByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public AppUserDto register(RegisterReq registerReq, Locale locale) throws CoreApplicationException {
        AppUserDto result = new AppUserDto();
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
}
