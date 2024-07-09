package com.study8.sys.auth.enumf;

/**
 * SendOTPEnum
 * @Date: 2024-07-09
 * @Author: HuyNH
 * @Desc: Send OTP Enum
 */
public enum SendOTPEnum {
    PHONE_NUMBER("1"), //Send OTP
    EMAIL("2"); //Send email

    private final String value;

    SendOTPEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SendOTPEnum resolveByValue(String value) {
        for (SendOTPEnum enumValue : SendOTPEnum.values()) {
            if (enumValue.value.equals(value)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("Invalid SendOTPEnum value: " + value);
    }
}
