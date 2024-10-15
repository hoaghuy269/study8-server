package com.study8.sys.system.enumf;

import com.study8.sys.system.constant.SystemErrorCodeConstant;
import lombok.Getter;

/**
 * SystemErrorEnum
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemErrorEnum
 */
@Getter //getCode(), getMessage()
public enum SystemErrorEnum {
    ERROR_DB_1001(SystemErrorCodeConstant.ERROR_DB_1001, "ERROR_DB_1001: Duplicate unique value");

    private final String code;

    private final String message;

    SystemErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
