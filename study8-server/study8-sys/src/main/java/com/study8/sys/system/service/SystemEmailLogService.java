package com.study8.sys.system.service;

import com.study8.sys.system.dto.SystemEmailLogDto;

/**
 * SystemEmailLogService
 * @Date: 2024-10-09
 * @Author: HuyNH
 * @Desc: SystemEmailLogService
 */
public interface SystemEmailLogService {
    void saveLog(SystemEmailLogDto systemEmailLogDto, Long createdId);
}
