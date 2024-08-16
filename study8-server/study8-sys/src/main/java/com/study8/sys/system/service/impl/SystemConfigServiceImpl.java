package com.study8.sys.system.service.impl;

import com.study8.sys.system.dto.SystemConfigDto;
import com.study8.sys.system.repository.SystemConfigRepository;
import com.study8.sys.system.service.SystemConfigService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SystemConfigServiceImpl
 * @Date: 2024-05-17
 * @Author: HuyNH
 * @Desc: System Config Service Impl
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {
    @Autowired
    SystemConfigRepository systemConfigRepository;

    @Override
    public Integer getIntValue(String code, String groupCode) {
        Integer result = null;
        SystemConfigDto systemConfigDto = this.findConfigByCodeAndGroupCode(code, groupCode);
        if (ObjectUtils.isNotEmpty(systemConfigDto)
                && StringUtils.isNotEmpty(systemConfigDto.getValue())) {
            result = Integer.valueOf(systemConfigDto.getValue());
        }
        return result;
    }

    @Override
    public String getStringValue(String code, String groupCode) {
        String result = null;
        SystemConfigDto systemConfigDto = this.findConfigByCodeAndGroupCode(code, groupCode);
        if (ObjectUtils.isNotEmpty(systemConfigDto)
                && StringUtils.isNotEmpty(systemConfigDto.getValue())) {
            result = systemConfigDto.getValue();
        }
        return result;
    }

    @Override
    public Map<String, String> getListStringValue(String groupCode) {
        Map<String, String> result = new HashMap<>();
        List<SystemConfigDto> systemConfigDtoList = systemConfigRepository
                .findListByGroupCode(groupCode);
        if (!CollectionUtils.isEmpty(systemConfigDtoList)) {
            return systemConfigDtoList.stream()
                    .filter(item -> StringUtils.isNotEmpty(item.getValue())
                            && StringUtils.isNotEmpty(item.getCode()))
                    .collect(Collectors.toMap(SystemConfigDto::getCode, SystemConfigDto::getValue));
        }
        return result;
    }

    @Override
    public Long getLongValue(String code, String groupCode) {
        Long result = null;
        SystemConfigDto systemConfigDto = this.findConfigByCodeAndGroupCode(code, groupCode);
        if (ObjectUtils.isNotEmpty(systemConfigDto)
                && StringUtils.isNotEmpty(systemConfigDto.getValue())) {
            result = Long.parseLong(
                    systemConfigDto.getValue());
        }
        return result;
    }

    private SystemConfigDto findConfigByCodeAndGroupCode(String code, String groupCode) {
        return systemConfigRepository.findByCodeAndGroupCode(code, groupCode);
    }
}
