package com.study8.sys.auth.req;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * LoginReq
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: Login request
 */
@NoArgsConstructor
@Getter
@Setter
public class LoginReq {
    @Size(max = 30)
    private String username;

    @Size(max = 50)
    private String password;
}
