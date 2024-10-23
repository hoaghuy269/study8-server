package com.study8.sys.system.enumf;

import lombok.Getter;

/**
 * SystemErrorPriorityEnum
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemErrorPriorityEnum
 */
@Getter
public enum SystemErrorPriorityEnum {
    LOW("1"),
    MEDIUM("2"),
    HIGH("3"),
    CRITICAL("4");

    private final String value;

    SystemErrorPriorityEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
