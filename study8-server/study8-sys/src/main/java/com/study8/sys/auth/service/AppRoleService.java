package com.study8.sys.auth.service;

import com.study8.sys.auth.dto.AppRoleDto;

/**
 * AppRoleService
 * @Date: 2024-09-06
 * @Author: HuyNH
 * @Desc: AppRole Service
 */
public interface AppRoleService {
    AppRoleDto getByName(String name);
}
