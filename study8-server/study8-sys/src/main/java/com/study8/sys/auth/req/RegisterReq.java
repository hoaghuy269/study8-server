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

    @Size(max = 20)
    @NotBlank
    @Pattern(regexp = SysConstant.PHONE_NUMBER_PATTERN)
    private String phoneNumber;

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = SysConstant.PASSWORD_PATTERN)
    private String password;
}
