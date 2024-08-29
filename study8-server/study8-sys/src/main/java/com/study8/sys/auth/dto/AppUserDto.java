package com.study8.sys.auth.dto;

import com.study8.core.dto.CoreDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * AppUserDto
 * @Date: 2024-06-01
 * @Author: HuyNH
 * @Desc: AppUser Dto
 */
@NoArgsConstructor
@Getter
@Setter
public class AppUserDto extends CoreDto {
    private Long id;
    private String code;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Integer active;

    public AppUserDto(Long id, String code, String username, String password,
                      String email, String phoneNumber, Integer active, LocalDateTime createdDate,
                      Long createdId, Integer deleted, LocalDateTime deletedDate, Long deletedId) {
        this.id = id;
        this.code = code;
        this.username = username;
        this.password = password;
        this.active = active;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.setCreatedDate(createdDate);
        this.setCreatedId(createdId);
        this.setDeleted(deleted);
        this.setDeletedDate(deletedDate);
        this.setDeletedId(deletedId);
    }
}
