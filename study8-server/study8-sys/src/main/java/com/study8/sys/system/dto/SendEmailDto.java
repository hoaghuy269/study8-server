package com.study8.sys.system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * SendEmailDto
 * @Date: 2024-07-26
 * @Author: HuyNH
 * @Desc: SendEmailDto
 */
@Getter
@Setter
@NoArgsConstructor
public class SendEmailDto {
    private String templateName;
    private String subject;
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    Map<String, Objects> mapData;
}
