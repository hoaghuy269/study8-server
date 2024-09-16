package com.study8.sys.auth.enumf;

import lombok.Getter;

/**
 * RoleEnum
 * @Date: 2024-06-01
 * @Author: HuyNH
 * @Desc: Roles of system
 */
@Getter
public enum RoleEnum {
    ROLE_ADMIN("ROLE_ADMIN"), //Admin
    ROLE_STUDENT("ROLE_STUDENT"), //Học sinh
    ROLE_TEACHER("ROLE_TEACHER"), //Giáo viên
    ROLE_VISITOR("ROLE_VISITOR"), //Khách vãn lai
    UNKNOWN("UNKNOWN"); //Unknown

    private final String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public static RoleEnum resolveByValue(String value) {
        for (RoleEnum enumValue : RoleEnum.values()) {
            if (enumValue.value.equals(value)) {
                return enumValue;
            }
        }
        return UNKNOWN;
    }
}
