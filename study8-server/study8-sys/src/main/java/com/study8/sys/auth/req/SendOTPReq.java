package com.study8.sys.auth.req;

import com.study8.sys.constant.SysConstant;
import jakarta.validation.constraints.*;

/**
 * SendOTPReq
 * @Date: 2024-07-09
 * @Author: HuyNH
 * @Desc: Send OTP Request
 */
public class SendOTPReq {
    @NotBlank
    @Size(max = 30)
    private String username;

    @Size(max = 20)
    @NotBlank
    @Pattern(regexp = SysConstant.PHONE_NUMBER_PATTERN)
    private String phoneNumber;

    @Size(max = 255)
    @NotBlank
    @Pattern(regexp = SysConstant.EMAIL_PATTERN)
    private String email;

    @NotNull
    @Min(1)
    @Max(2)
    private Integer type;
}
