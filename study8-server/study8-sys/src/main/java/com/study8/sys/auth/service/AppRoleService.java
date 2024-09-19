package com.study8.sys.auth.service;

import com.study8.sys.auth.dto.AppRoleDto;
import com.study8.sys.auth.entity.AppRole;

import java.util.Set;

/**
 * AppRoleService
 * @Date: 2024-09-06
 * @Author: HuyNH
 * @Desc: AppRole Service
 */
public interface AppRoleService {
    AppRoleDto getByName(String name);

    Set<AppRole> getSetByUserId(Long userId);
}
