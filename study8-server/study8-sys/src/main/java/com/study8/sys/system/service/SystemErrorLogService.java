package com.study8.sys.system.service;

import com.study8.sys.system.enumf.SystemErrorEnum;
import com.study8.sys.system.enumf.SystemErrorPriorityEnum;

import java.util.Locale;

/**
 * SystemErrorLogService
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemErrorLogService
 */
public interface SystemErrorLogService {
    void saveErrorLog(SystemErrorEnum errorCode,
                      SystemErrorPriorityEnum errorPriority,
                      Locale locale,
                      boolean isSendEmail);
}
