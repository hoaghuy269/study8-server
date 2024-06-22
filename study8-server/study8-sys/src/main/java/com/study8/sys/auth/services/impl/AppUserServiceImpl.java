package com.study8.sys.auth.services.impl;

import com.study8.core.constant.CoreExceptionMessageConstant;
import com.study8.core.exception.CoreApplicationException;
import com.study8.core.util.CoreExceptionUtils;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.repository.AppUserRepository;
import com.study8.sys.auth.services.AppUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    AppUserRepository appUserRepository;

    @Override
    public AppUserDto getByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
