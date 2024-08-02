package com.study8.sys.system.service.impl;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.constant.AuthExceptionConstant;
import com.study8.sys.constant.ExceptionConstant;
import com.study8.sys.constant.SignConstant;
import com.study8.sys.constant.SysConstant;
import com.study8.sys.system.dto.SendEmailDto;
import com.study8.sys.system.dto.SendEmailResultDto;
import com.study8.sys.system.enumf.EmailEnum;
import com.study8.sys.system.enumf.LanguageEnum;
import com.study8.sys.system.service.EmailService;
import com.study8.sys.util.ExceptionUtils;
import com.study8.sys.util.ResourceUtils;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
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
    public SendEmailResultDto sendEmailSMTP(SendEmailDto sendEmailDto, Locale locale) {
        SendEmailResultDto sendEmailResultDto = new SendEmailResultDto();
        LocalDateTime now = LocalDateTime.now();
        if (CollectionUtils.isEmpty(sendEmailDto.getTo())) { //Not allow sendEmailDto.to empty
            sendEmailResultDto.setIsSuccess(false);
            sendEmailResultDto.setErrorMessage(
                    ResourceUtils.getMessage(ExceptionConstant.EXCEPTION_DATA_PROCESSING,
                            locale));
            sendEmailResultDto.setTime(now);
            return sendEmailResultDto;
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(sendEmailDto.getTo().toArray(new String[0]));
            if (!CollectionUtils.isEmpty(sendEmailDto.getCc())) { //Cc
                mimeMessageHelper.setCc(sendEmailDto.getCc().toArray(new String[0]));
            }
            if (!CollectionUtils.isEmpty(sendEmailDto.getBcc())) { //Cc
                mimeMessageHelper.setCc(sendEmailDto.getCc().toArray(new String[0]));
            }
            mimeMessageHelper.setSubject(sendEmailDto.getSubject()); //Subject
            mimeMessageHelper.setText(this.getTemplateResource(sendEmailDto.getTemplateCode(),
                    locale.getLanguage()), true); //Content
            //Do send mail
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("EmailServiceImpl | sendEmailSMTP", e);
            sendEmailResultDto.setIsSuccess(false);
            sendEmailResultDto.setErrorMessage(e.getMessage());
            sendEmailResultDto.setTime(now);
        }
        return sendEmailResultDto;
    }

    private String getTemplateResource(String templateCode, String language) {
        String templateName = EmailEnum.resolveByValue(templateCode)
                .toString();
        String templateLanguage = LanguageEnum.resolveByValue(language)
                .toString();
        String templateFullName = templateName + SignConstant.DASH + templateLanguage;
        return ResourceUtils.getStringResource(
                SysConstant.RESOURCE_EMAIL_TEMPLATES + SignConstant.SPLASH +
                        templateFullName);
    }
}
