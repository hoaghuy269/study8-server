package com.study8.sys.system.enumf;

import com.study8.sys.auth.enumf.SendOTPEnum;

/**
 * EmailEnum
 * @Date: 2024-08-01
 * @Author: HuyNH
 * @Desc: Email Enum
 */
public enum EmailEnum {
    OTP_EMAIL("email_001"), //Send OTP email
    UNKNOWN("unknown");

    private final String value;

    EmailEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static EmailEnum resolveByValue(String value) {
        for (EmailEnum enumValue : EmailEnum.values()) {
            if (enumValue.value.equals(value)) {
                return enumValue;
            }
        }
        return UNKNOWN;
    }
}
