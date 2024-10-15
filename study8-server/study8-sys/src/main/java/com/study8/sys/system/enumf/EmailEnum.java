package com.study8.sys.system.enumf;

/**
 * EmailEnum
 * @Date: 2024-08-01
 * @Author: HuyNH
 * @Desc: Email Enum
 */
public enum EmailEnum {
    OTP_EMAIL("email_001"),
    FORGOT_PASSWORD_EMAIL("email_003"),
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
