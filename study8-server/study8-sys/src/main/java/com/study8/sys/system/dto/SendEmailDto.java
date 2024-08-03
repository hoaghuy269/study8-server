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
    private String templateCode;
    private String subject;
    private List<String> to; //Not allow null
    private List<String> cc; //Allow null
    private List<String> bcc; //Allow null
    Map<String, Objects> mapData;
}
