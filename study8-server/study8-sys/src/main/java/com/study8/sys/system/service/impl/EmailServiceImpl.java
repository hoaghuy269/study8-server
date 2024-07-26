package com.study8.sys.system.service.impl;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.constant.AuthExceptionConstant;
import com.study8.sys.system.dto.SendEmailDto;
import com.study8.sys.system.dto.SendEmailResultDto;
import com.study8.sys.system.service.EmailService;
import com.study8.sys.util.ExceptionUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.ApplicationContextException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Locale;

/**
 * EmailServiceImpl
 * @Date: 2024-07-18
 * @Author: HuyNH
 * @Desc: Email Service Impl
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public SendEmailResultDto sendEmailSMTP(SendEmailDto sendEmailDto, Locale locale)
            throws CoreApplicationException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        if (CollectionUtils.isEmpty(sendEmailDto.getTo())) { //Not allow sendEmailDto.to empty
            ExceptionUtils.throwCoreApplicationException(
                    AuthExceptionConstant.EXCEPTION_AUTH_PHONE_NUMBER_EXITS, locale);
        }
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(sendEmailDto.getTo().toArray(new String[0]));
            if (!CollectionUtils.isEmpty(sendEmailDto.getCc())) { //Cc
                mimeMessageHelper.setCc(sendEmailDto.getCc().toArray(new String[0]));
            }
            if (!CollectionUtils.isEmpty(sendEmailDto.getBcc())) { //Cc
                mimeMessageHelper.setCc(sendEmailDto.getCc().toArray(new String[0]));
            }
        } catch (Exception e) {
            log.error("EmailServiceImpl | sendEmailSMTP", e);
            ExceptionUtils.throwCoreApplicationException(
                    e.getMessage());
        }
        javaMailSender.send(mimeMessage);
        return null;
    }
}
