package com.study8.sys.auth.req;

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
    private String fullName;

    @Size(max = 50)
    private String emailOrPhone;
    private String password;
}
