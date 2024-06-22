package com.study8.sys.system.service.impl;

import com.study8.sys.system.dto.SystemConfigDto;
import com.study8.sys.system.repository.SystemConfigRepository;
import com.study8.sys.system.service.SystemConfigService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        SystemConfigDto systemConfigDto = systemConfigRepository.findByCodeAndGroupCode(code, groupCode);
        if (ObjectUtils.isNotEmpty(systemConfigDto)
                && StringUtils.isNotEmpty(systemConfigDto.getValue())) {
            result = Integer.valueOf(systemConfigDto.getValue());
        }
        return result;
    }
}
