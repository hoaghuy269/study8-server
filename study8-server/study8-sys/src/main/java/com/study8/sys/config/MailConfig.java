package com.study8.sys.config;

import com.study8.sys.constant.SysConstant;
import com.study8.sys.system.service.SystemConfigService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Map;
import java.util.Properties;

/**
 * MailConfig
 * @Date: 2024-07-18
 * @Author: HuyNH
 * @Desc: Mail Config
 */
@Getter
@Configuration
public class MailConfig {
    private String mailHost;
    private int mailPort;
    private String emailServer;

    @Bean
    @Lazy
    public JavaMailSender javaMailSender(
            @Value("${spring.mail.host}") String mailHost,
            @Value("${spring.mail.port}") int mailPort,
            @Value("${spring.mail.properties.mail.smtp.auth}") boolean mailSmtpAuth,
            @Value("${spring.mail.properties.mail.smtp.starttls.enable}") boolean mailSmtpStarttlsEnable,
            SystemConfigService systemConfigService) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);

        Map<String, String> emailConfigMap = systemConfigService
                .getListStringValue(SysConstant.EMAIL);
        mailSender.setUsername(emailConfigMap.get(SysConstant.EMAIL_USERNAME));
        mailSender.setPassword(emailConfigMap.get(SysConstant.EMAIL_PASSWORD));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", String.valueOf(mailSmtpAuth));
        props.put("mail.smtp.starttls.enable", String.valueOf(mailSmtpStarttlsEnable));
        props.put("mail.debug", "true");

        this.mailHost = mailHost;
        this.mailPort = mailPort;
        this.emailServer = mailSender.getUsername();

        return mailSender;
    }

}
