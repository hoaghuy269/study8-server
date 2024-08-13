package com.study8.sys.util;

import com.study8.sys.service.UserDetailsImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Class: UserProfileUtils
 * @Date: 2024-08-12
 * @Author: HuyNH
 * @Desc: UserProfile Utils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserProfileUtils {
    public static Authentication getAuthentication() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }

    public static UserDetailsImpl getUserDetail() {
        if (ObjectUtils.isNotEmpty(getAuthentication())
                && getAuthentication().getPrincipal()
                instanceof UserDetailsImpl userDetails) {
            return userDetails;
        }
        return null;
    }


    public static Long getUserId() {
        return Optional.ofNullable(getUserDetail())
                .map(UserDetailsImpl::getId)
                .orElse(null);
    }
}
