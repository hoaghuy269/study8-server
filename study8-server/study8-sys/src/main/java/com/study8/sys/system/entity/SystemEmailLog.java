package com.study8.sys.system.entity;

import com.study8.core.entity.CoreEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * system_email_log
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemEmailLog
 */
@Entity
@Table(name = "system_email_log")
@Getter
@Setter
@NoArgsConstructor
public class SystemEmailLog extends CoreEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email_server")
    private String emailServer;

    @Column(name = "email_server_ip")
    private String emailServerIp;

    @Column(name = "email_host")
    private String emailHost;

    @Column(name = "email_port")
    private Integer emailPort;

    @Column(name = "email_to")
    private String emailTo;

    @Column(name = "email_cc")
    private String emailCc;

    @Column(name = "email_bcc")
    private String emailBcc;

    @Column(name = "email_template_code")
    private String emailTemplateCode;

    @Column(name = "email_subject")
    private String emailSubject;

    @Column(name = "email_content")
    private String emailContent;

    @Column(name = "sent_date")
    private LocalDateTime sentDate;

    @Column(name = "sent_status")
    private Integer sentStatus;
}
