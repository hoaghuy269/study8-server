package com.study8.sys.system.dto;

import com.study8.core.dto.CoreDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * SystemEmailLogDto
 * @Date: 2024-10-09
 * @Author: HuyNH
 * @Desc: SystemEmailLogDto
 */
@Getter
@Setter
@NoArgsConstructor
public class SystemEmailLogDto extends CoreDto {
    private Long id;
    private String emailServer;
    private String emailServerIp;
    private String emailHost;
    private Integer emailPort;
    private String emailTo;
    private String emailCc;
    private String emailBcc;
    private String emailTemplateCode;
    private String emailSubject;
    private String emailContent;
    private LocalDateTime sentDate;
    private Integer sentStatus;
}
