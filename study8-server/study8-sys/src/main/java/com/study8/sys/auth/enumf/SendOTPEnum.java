package com.study8.sys.auth.enumf;

import lombok.Getter;

/**
 * SendOTPEnum
 * @Date: 2024-07-09
 * @Author: HuyNH
 * @Desc: Send OTP Enum
 */
@Getter
public enum SendOTPEnum {
    PHONE_NUMBER(1), //Send OTP
    EMAIL(2); //Send email

    private final Integer value;

    SendOTPEnum(Integer value) {
        this.value = value;
    }

    public static SendOTPEnum resolveByValue(Integer value) {
        for (SendOTPEnum enumValue : SendOTPEnum.values()) {
            if (enumValue.value.equals(value)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("Invalid SendOTPEnum value: " + value);
    }
}
