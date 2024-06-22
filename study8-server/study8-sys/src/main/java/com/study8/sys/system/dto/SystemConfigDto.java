package com.study8.sys.system.dto;

import com.study8.core.dto.CoreDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * SystemConfigDto
 * @Date: 2024-05-11
 * @Author: HuyNH
 * @Desc:
 */
@NoArgsConstructor
@Getter
@Setter
public class SystemConfigDto extends CoreDto {
    private String groupCode;
    private String code;
    private String value;

    public SystemConfigDto(String groupCode, String code, String value,
                           LocalDateTime createdDate, Long createdId,
                           Integer deleted, LocalDateTime deletedDate,
                           Long deletedId) {
        this.groupCode = groupCode;
        this.code = code;
        this.value = value;
        this.setCreatedDate(createdDate);
        this.setCreatedId(createdId);
        this.setDeleted(deleted);
        this.setDeletedDate(deletedDate);
        this.setDeletedId(deletedId);
    }
}
