package com.study8.sys.system.service;

import com.study8.sys.system.dto.SendEmailDto;
import com.study8.sys.system.dto.SendEmailResultDto;

import java.util.Locale;

/**
 * EmailService
 * @Date: 2024-07-18
 * @Author: HuyNH
 * @Desc: Service Send Email SMTP
 */
public interface EmailService {
    SendEmailResultDto sendEmailSMTP(SendEmailDto sendEmailDto, Locale locale);
}
