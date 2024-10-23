package com.study8.sys.auth.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ForgotPasswordRes
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: Forgot Password Response
 */
@Getter
@Setter
@NoArgsConstructor
public class ForgotPasswordRes {
    private Boolean isAccountValid;
    private Boolean isSendOTP;
    private String urlResetPassword;
}
