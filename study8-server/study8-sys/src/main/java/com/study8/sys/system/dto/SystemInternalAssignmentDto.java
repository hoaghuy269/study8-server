package com.study8.sys.system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SystemInternalAssignmentDto
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemInternalAssignmentDto
 */
@Getter
@Setter
@NoArgsConstructor
public class SystemInternalAssignmentDto {
    private Long id;
    private String groupCode;
    private String code;
    private Long assigneeId;
    private Boolean active;
}
