package com.study8.sys.system.service.impl;

import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.service.AppUserService;
import com.study8.sys.config.SettingVariable;
import com.study8.sys.constant.SysConstant;
import com.study8.sys.system.dto.SystemConstantDto;
import com.study8.sys.system.dto.SystemInternalAssignmentDto;
import com.study8.sys.system.entity.SystemErrorLog;
import com.study8.sys.system.enumf.SystemErrorEnum;
import com.study8.sys.system.enumf.SystemErrorPriorityEnum;
import com.study8.sys.system.repository.SystemErrorLogRepository;
import com.study8.sys.system.service.EmailService;
import com.study8.sys.system.service.SystemConstantService;
import com.study8.sys.system.service.SystemErrorLogService;
import com.study8.sys.system.service.SystemInternalAssignmentService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * SystemErrorLogServiceImpl
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemErrorLogServiceImpl
 */
@Service
@Slf4j
public class SystemErrorLogServiceImpl
        implements SystemErrorLogService {
    @Autowired
    private SystemErrorLogRepository systemErrorLogRepository;

    @Autowired
    private SystemConstantService systemConstantService;

    @Autowired
    private SystemInternalAssignmentService systemInternalAssignmentService;

    @Autowired
    @Lazy
    private AppUserService appUserService;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private EmailService emailService;

    @Override
    public void saveErrorLog(SystemErrorEnum errorCode,
                             SystemErrorPriorityEnum errorPriority,
                             Locale locale,
                             boolean isSendEmail) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(
                platformTransactionManager);
        Runnable saveErrorLog = () -> transactionTemplate
                .execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {
                        log.info("SystemErrorLogServiceImpl | saveErrorLog | Start thread");

                        try {
                            SystemErrorLog systemErrorLog = new SystemErrorLog();
                            systemErrorLog.setCode(errorCode.getCode());
                            systemErrorLog.setMessage(errorCode.getMessage());
                            systemErrorLog.setServiceName(SettingVariable.SERVICE_NAME);

                            SystemConstantDto priority = systemConstantService
                                    .getByGroupCodeAndCode(SysConstant.SYSTEM_ERROR_PRIORITY,
                                            errorPriority.toString());
                            systemErrorLog.setPriority(Integer.valueOf(priority.getCode()));
                            systemErrorLog.setPriorityName(priority.getCode()
                                    + " - "
                                    + priority.getName());

                            List<SystemInternalAssignmentDto> systemInternalAssignmentDtoList = systemInternalAssignmentService
                                    .getListByGroupCode(SysConstant.SYSTEM_MAINTENANCE);
                            if (CollectionUtils.isNotEmpty(systemInternalAssignmentDtoList)) {
                                //Random staff support issue error
                                int randomAssignee = new SecureRandom()
                                        .nextInt(systemInternalAssignmentDtoList.size());

                                SystemInternalAssignmentDto systemInternalAssignmentDto = systemInternalAssignmentDtoList
                                        .get(randomAssignee);

                                //Assign
                                AppUserDto appUserDto = appUserService.getById(
                                        systemInternalAssignmentDto.getAssigneeId());
                                if (ObjectUtils.isNotEmpty(appUserDto)) {
                                    systemErrorLog.setAssigneeId(systemInternalAssignmentDto.getAssigneeId());
                                    systemErrorLog.setAssigneeName(appUserDto.getUsername());
                                    if (StringUtils.isNotEmpty(appUserDto.getEmail())
                                            && appUserDto.getEmailVerified()) {
                                        systemErrorLog.setAssigneeEmail(appUserDto.getEmail());
                                        if (isSendEmail) {
                                            //Do send email
                                            this.sendEmail();
                                            systemErrorLog.setSentEmail(true);
                                            systemErrorLog.setSentEmailDate(LocalDateTime.now());
                                        } else {
                                            systemErrorLog.setSentEmail(false);
                                        }
                                    }
                                }
                            }
                            systemErrorLog.setCreatedId(SettingVariable.SYSTEM_ADMIN_ID);
                            systemErrorLog.setCreatedDate(LocalDateTime.now());

                            //Do save
                            systemErrorLogRepository.save(systemErrorLog);
                        } catch (Exception e) {
                            log.error("SystemErrorLogServiceImpl | saveErrorLog", e);
                        }

                        log.info("SystemErrorLogServiceImpl | saveErrorLog | End thread");
                    }

                    private void sendEmail() {
                        //TODO: Send email
                    }
                });
        Thread thread = new Thread(saveErrorLog);
        thread.start();
    }
}
