package com.study8.sys.auth.dto;

import com.study8.sys.auth.enumf.RoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AppRoleDto
 * @Date: 2024-09-06
 * @Author: HuyNH
 * @Desc: AppRole Dto
 */
@NoArgsConstructor
@Getter
@Setter
public class AppRoleDto {
    private Long id;
    private RoleEnum name;
}
