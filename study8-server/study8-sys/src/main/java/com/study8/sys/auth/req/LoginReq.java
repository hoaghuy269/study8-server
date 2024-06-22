package com.study8.sys.auth.req;

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
    private String username;
    private String password;
}
