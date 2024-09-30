package com.study8.sys.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ExceptionConstant
 * @Date: 2024-06-23
 * @Author: HuyNH
 * @Desc: Exception Constant
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionConstant {
    public static final String EXCEPTION_DATA_PROCESSING = "EXCEPTION_DATA_PROCESSING";
    public static final String EXCEPTION_ACCOUNT_DOES_NOT_HAVE_PERMISSION = "EXCEPTION_ACCOUNT_DOES_NOT_HAVE_PERMISSION";
    public static final String EXCEPTION_TRY_AGAIN = "EXCEPTION_TRY_AGAIN";
}
