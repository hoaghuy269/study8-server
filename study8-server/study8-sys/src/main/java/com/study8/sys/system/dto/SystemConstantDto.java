package com.study8.sys.system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SystemConstantDto
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemConstantDto
 */
@Getter
@Setter
@NoArgsConstructor
public class SystemConstantDto {
    private Long id;
    private String serviceName;
    private String groupCode;
    private String name;
    private String kind;
    private String code;
    private String lang;
    private Boolean active;
}
