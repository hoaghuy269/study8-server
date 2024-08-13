package com.study8.sys.system.service.impl;

import com.study8.sys.constant.ContentConstant;
import com.study8.sys.constant.ExceptionConstant;
import com.study8.sys.constant.SignConstant;
import com.study8.sys.constant.SysConstant;
import com.study8.sys.system.dto.SendEmailDto;
import com.study8.sys.system.dto.SendEmailResultDto;
import com.study8.sys.system.enumf.EmailEnum;
import com.study8.sys.system.enumf.LanguageEnum;
import com.study8.sys.system.service.EmailService;
import com.study8.sys.system.validator.EmailValidator;
import com.study8.sys.util.ResourceUtils;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;

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

    @Autowired
    private EmailValidator emailValidator;

    @Override
    public SendEmailResultDto sendEmailSMTP(SendEmailDto sendEmailDto, Locale locale) {
        SendEmailResultDto sendEmailResultDto = new SendEmailResultDto();
        LocalDateTime currentDate = LocalDateTime.now();
        if (Boolean.FALSE.equals(emailValidator.isDataValid(sendEmailDto))) { //Validate before send email
            sendEmailResultDto.setIsSuccess(false);
            sendEmailResultDto.setErrorMessage(
                    ResourceUtils.getMessage(ExceptionConstant.EXCEPTION_DATA_PROCESSING,
                            locale));
            sendEmailResultDto.setTime(currentDate);
            return sendEmailResultDto;
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(sendEmailDto.getTo().toArray(new String[0]));
            if (CollectionUtils.isNotEmpty(sendEmailDto.getCc())) { //cc
                mimeMessageHelper.setCc(sendEmailDto.getCc().toArray(new String[0]));
            }
            if (CollectionUtils.isNotEmpty(sendEmailDto.getBcc())) { //bcc
                mimeMessageHelper.setCc(sendEmailDto.getCc().toArray(new String[0]));
            }
            mimeMessageHelper.setSubject(sendEmailDto.getSubject()); //Subject
            mimeMessageHelper.setText(this.getTemplateResource(sendEmailDto.getTemplateCode(),
                    locale.getLanguage(), sendEmailDto.getMapData()), true); //Content
            //Do send mail
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("EmailServiceImpl | sendEmailSMTP", e);
            sendEmailResultDto.setIsSuccess(false);
            sendEmailResultDto.setErrorMessage(e.getMessage());
            sendEmailResultDto.setTime(currentDate);
        }
        //Success
        sendEmailResultDto.setIsSuccess(true);
        sendEmailResultDto.setTime(currentDate);
        return sendEmailResultDto;
    }

    private String getTemplateResource(String templateCode, String language, Map<String, Object> mapData) {
        String templateName = EmailEnum.resolveByValue(templateCode)
                .toString();
        String templateLanguage = LanguageEnum.resolveByValue(language)
                .toString();
        StringBuilder templateFullNameBuilder = new StringBuilder();
        String htmlTemplate = ResourceUtils.getStringResource(
                SysConstant.RESOURCE_EMAIL_TEMPLATES + SignConstant.SPLASH +
                        templateFullNameBuilder.append(templateName)
                                .append(SignConstant.DASH)
                                .append(templateLanguage)
                                .append(SignConstant.DOT)
                                .append(ContentConstant.HTML));
        StringSubstitutor stringSubstitutor = new StringSubstitutor(mapData);
        return stringSubstitutor
                .replace(htmlTemplate);
    }
}
