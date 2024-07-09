package com.study8.sys.auth.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * SendOTPRes
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: Send OTP response
 */
@NoArgsConstructor
@Getter
@Setter
public class SendOTPRes {
    private String username;
    private Boolean isSendOTP;
    private LocalDateTime expirationDate;
}
