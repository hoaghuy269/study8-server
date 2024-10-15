package com.study8.sys.auth.service;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.entity.AppUser;
import com.study8.sys.auth.req.ForgotPasswordReq;
import com.study8.sys.auth.req.RegisterReq;
import com.study8.sys.auth.res.ForgotPasswordRes;
import com.study8.sys.auth.res.ResetPasswordRes;

import java.util.List;
import java.util.Locale;

/**
 * AppUserService
 * @Date: 2024-06-01
 * @Author: HuyNH
 * @Desc: AppUser Service
 */
public interface AppUserService {
    AppUserDto getByUsername(String username);

    AppUserDto register(RegisterReq registerReq, Locale locale)
            throws CoreApplicationException;

    List<AppUserDto> getListByPhoneNumber(String phoneNumber);

    AppUser activeAccount(Long userId, Integer activeType);

    void updateAccount(AppUserDto appUserDto, boolean isUpdateInNewThread);

    boolean isEmailVerified(String email);

    AppUserDto getById(Long id);

    List<AppUserDto> getListByEmail(String email);

    ForgotPasswordRes forgotPassword(ForgotPasswordReq forgotPasswordReq, Locale locale)
            throws CoreApplicationException;

    ResetPasswordRes resetPassword(String code, String newPassword)
            throws CoreApplicationException;
}
