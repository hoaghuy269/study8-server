package com.study8.sys.auth.req;

import com.study8.sys.constant.SysConstant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ForgotPasswordReq
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: ForgotPasswordReq
 */
@Getter
@Setter
@NoArgsConstructor
public class ForgotPasswordReq {
    @Size(max = 30)
    private String username;

    @Size(max = 20)
    @Pattern(regexp = SysConstant.PHONE_NUMBER_PATTERN)
    private String phoneNumber;

    @Size(max = 255)
    @Pattern(regexp = SysConstant.EMAIL_PATTERN)
    private String email;

    @NotNull
    private Integer otpType;
}
