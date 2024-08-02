package com.study8.sys.system.enumf;

import lombok.Getter;

/**
 * LanguageEnum
 * @Date: 2024-08-01
 * @Author: HuyNH
 * @Desc: Language Enum
 */
@Getter
public enum LanguageEnum {
    VI("vi"), // Vietnamese
    EN("en"), // English
    UNKNOWN("unknown"); // Unknown

    private final String value;

    LanguageEnum(String value) {
        this.value = value;
    }

    public static LanguageEnum resolveByValue(String value) {
        for (LanguageEnum enumValue : LanguageEnum.values()) {
            if (enumValue.value.equals(value)) {
                return enumValue;
            }
        }
        return UNKNOWN;
    }
}
