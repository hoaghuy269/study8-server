package com.study8.sys.auth.req;

import com.study8.sys.constant.SysConstant;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * RegisterReq
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: Register request
 */
@NoArgsConstructor
@Getter
@Setter
public class RegisterReq {
    @NotBlank
    @Size(max = 30)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = SysConstant.FULL_NAME_PATTERN)
    private String fullName;

    @Size(max = 255)
    @Pattern(regexp = SysConstant.EMAIL_PATTERN)
    private String email;

    @Size(max = 11)
    @Pattern(regexp = SysConstant.PHONE_NUMBER_PATTERN)
    private String phoneNumber;

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = SysConstant.PASSWORD_PATTERN)
    private String password;

    @NotNull
    @Min(1)
    @Max(2)
    private Integer role;
}
