package com.study8.sys.system.service;

import com.study8.sys.system.dto.SystemConstantDto;

/**
 * SystemConstantService
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemErrorLogService
 */
public interface SystemConstantService {
    SystemConstantDto getByGroupCodeAndCode(String groupCode, String code);
}
