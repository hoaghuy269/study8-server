package com.study8.sys.system.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * VerifyOTPRes
 * @Date: 2024-08-14
 * @Author: HuyNH
 * @Desc: Send OTP response
 */
@Getter
@Setter
@NoArgsConstructor
public class VerifyOTPRes {
    private String username;
    private Boolean isVerified;
    private LocalDateTime dateOfVerification;
}
