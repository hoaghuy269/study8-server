package com.study8.sys.auth.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study8.sys.auth.dto.AppRoleDto;
import com.study8.sys.auth.entity.AppRole;
import com.study8.sys.auth.enumf.RoleEnum;
import com.study8.sys.auth.repository.AppRoleRepository;
import com.study8.sys.auth.service.AppRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * AppRoleServiceImpl
 * @Date: 2024-06-01
 * @Author: HuyNH
 * @Desc: AppRole Service Implement
 */
@Service
@Slf4j
public class AppRoleServiceImpl implements AppRoleService {
    @Autowired
    private AppRoleRepository appRoleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public AppRoleDto getByName(String name) {
        RoleEnum roleEnum = RoleEnum.resolveByValue(name);
        AppRole appRole = appRoleRepository.findByName(roleEnum);
        if (ObjectUtils.isNotEmpty(appRole)) {
            return objectMapper.convertValue(
                    appRole, AppRoleDto.class);
        }
        return null;
    }

    @Override
    public Set<AppRole> getSetByUserId(Long userId) {
        return appRoleRepository
                .findByUserId(userId);
    }
}
