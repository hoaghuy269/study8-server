package com.study8.sys.system.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study8.sys.system.dto.SystemEmailLogDto;
import com.study8.sys.system.entity.SystemEmailLog;
import com.study8.sys.system.repository.SystemEmailLogRepository;
import com.study8.sys.system.service.SystemEmailLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * SystemEmailLogServiceImpl
 * @Date: 2024-10-09
 * @Author: HuyNH
 * @Desc: SystemEmailLogServiceImpl
 */
@Service
public class SystemEmailLogServiceImpl implements SystemEmailLogService {
    @Autowired
    private SystemEmailLogRepository systemEmailLogRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void saveLog(SystemEmailLogDto systemEmailLogDto, Long createdId) {
        SystemEmailLog systemEmailLog = objectMapper
                .convertValue(
                        systemEmailLogDto,
                        SystemEmailLog.class);
        systemEmailLog.setCreatedDate(LocalDateTime.now());
        systemEmailLog.setCreatedId(createdId);

        //Do save
        systemEmailLogRepository.save(systemEmailLog);
    }
}
