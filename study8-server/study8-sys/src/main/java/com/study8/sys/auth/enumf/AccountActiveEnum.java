package com.study8.sys.auth.enumf;

import lombok.Getter;

/**
 * AccountActiveEnum
 * @Date: 2024-06-28
 * @Author: HuyNH
 * @Desc: Active enum of account
 */
@Getter
public enum AccountActiveEnum {
    ACTIVE(1), //Đang hoạt động
    INACTIVE(0) //Bị khóa
    ;

    private final int value;

    AccountActiveEnum(int value) {
        this.value = value;
    }
}
