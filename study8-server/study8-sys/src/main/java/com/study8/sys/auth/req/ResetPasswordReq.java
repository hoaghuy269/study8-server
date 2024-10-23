package com.study8.sys.auth.req;

import com.study8.sys.constant.SysConstant;
import jakarta.validation.constraints.NotBlank;
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
public class ResetPasswordReq {
    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = SysConstant.PASSWORD_PATTERN)
    private String password;

    @Size(max = 50)
    @NotBlank
    private String otpCode;
}
