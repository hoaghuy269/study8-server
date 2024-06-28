package com.study8.sys.auth.req;

import com.study8.sys.constant.SysConstant;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(max = 50)
    @Pattern(regexp = SysConstant.FULL_NAME_PATTERN)
    private String fullName;

    @Size(max = 50)
    private String emailOrPhoneNumber;

    @Size(max = 100)
    @Pattern(regexp = SysConstant.PASSWORD_PATTERN)
    private String password;

    //TODO: Tìm giải pháp thêm role khi tạo tài khoản
//    private Integer role;
}
