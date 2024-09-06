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
    private Boolean emailVerified;
    private Boolean phoneNumberVerified;
}
