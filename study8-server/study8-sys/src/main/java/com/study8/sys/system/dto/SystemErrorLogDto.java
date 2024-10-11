package com.study8.sys.system.dto;

import com.study8.core.dto.CoreDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * SystemErrorLogDto
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemErrorLogDto
 */
@Getter
@Setter
@NoArgsConstructor
public class SystemErrorLogDto extends CoreDto {
    private Long id;
    private String code;
    private String message;
    private String serviceName;
    private String service_log;
    private Integer priority;
    private String priorityName;
    private Long assigneeId;
    private String assigneeName;
    private String assigneeEmail;
    private Boolean sentEmail;
    private LocalDateTime sentEmailDate;
    private Boolean fixed;
    private LocalDateTime fixedDate;
    private String fixedBy;
    private String note;
}
