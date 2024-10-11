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
 * SystemErrorLog
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemErrorLog
 */
@Entity
@Table(name = "system_error_log")
@Getter
@Setter
@NoArgsConstructor
public class SystemErrorLog extends CoreEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "message")
    private String message;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_log")
    private String service_log;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "priority_name")
    private String priorityName;

    @Column(name = "assignee_id")
    private Long assigneeId;

    @Column(name = "assignee_name")
    private String assigneeName;

    @Column(name = "assignee_email")
    private String assigneeEmail;

    @Column(name = "sent_email")
    private Boolean sentEmail;

    @Column(name = "sent_email_date")
    private LocalDateTime sentEmailDate;

    @Column(name = "fixed")
    private Boolean fixed;

    @Column(name = "fixed_date")
    private LocalDateTime fixedDate;

    @Column(name = "fixed_by")
    private String fixedBy;

    @Column(name = "note")
    private String note;
}
