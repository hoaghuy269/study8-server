package com.study8.sys.system.service;

import com.study8.sys.system.dto.SystemConfigDto;

import java.util.List;
import java.util.Map;

/**
 * SystemConfigService
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: System Config Service
 */
public interface SystemConfigService {
    Integer getIntValue(String code, String groupCode);

    String getStringValue(String code, String groupCode);

    Map<String, String> getListStringValue(String groupCode);

    Long getLongValue(String code, String groupCode);
}
