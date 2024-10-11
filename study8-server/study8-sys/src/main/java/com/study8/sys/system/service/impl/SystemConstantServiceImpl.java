package com.study8.sys.system.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study8.sys.system.dto.SystemConstantDto;
import com.study8.sys.system.entity.SystemConstant;
import com.study8.sys.system.repository.SystemConstantRepository;
import com.study8.sys.system.service.SystemConstantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SystemConstantServiceImpl
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemConstantServiceImpl
 */
@Service
public class SystemConstantServiceImpl implements SystemConstantService {
    @Autowired
    private SystemConstantRepository systemConstantRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public SystemConstantDto getByGroupCodeAndCode(String groupCode, String code) {
        SystemConstant systemConstant = systemConstantRepository
                .findByGroupCodeAndCodeAndActiveIsTrue(groupCode, code);
        return objectMapper.convertValue(
                systemConstant, SystemConstantDto.class);
    }
}
