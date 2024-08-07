package com.study8.sys.config;

import com.study8.sys.constant.SysConstant;
import com.study8.sys.system.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Map;
import java.util.Properties;

/**
 * EmailConfig
 * @Date: 2024-07-18
 * @Author: HuyNH
 * @Desc: Email Config
 */
@Configuration
public class MailConfig {
    @Autowired
    SystemConfigService systemConfigService;

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean mailSmtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean mailSmtpStarttlsEnable;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        // Email username, password
        Map<String, String> emailConfigMap = systemConfigService
                .getListStringValue(SysConstant.EMAIL);
        mailSender.setUsername(emailConfigMap.get(SysConstant.EMAIL_USERNAME));
        mailSender.setPassword(emailConfigMap.get(SysConstant.EMAIL_PASSWORD));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", String.valueOf(mailSmtpAuth));
        props.put("mail.smtp.starttls.enable", String.valueOf(mailSmtpStarttlsEnable));
        props.put("mail.debug", "true");

        return mailSender;
    }
}
