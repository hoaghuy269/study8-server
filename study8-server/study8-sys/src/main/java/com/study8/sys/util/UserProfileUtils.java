package com.study8.sys.util;

import com.study8.sys.auth.enumf.RoleEnum;
import com.study8.sys.service.UserDetailsImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
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

    public static boolean hasRole(RoleEnum role) {
        Collection<? extends GrantedAuthority> roleEnums = Objects.requireNonNull(getUserDetail()).getAuthorities();
        return Optional.ofNullable(getUserDetail())
                .map(UserDetailsImpl::getAuthorities)
                .orElse(Collections.emptyList())
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role.getValue()));
    }

    public static boolean hasAnyRole(Collection<RoleEnum> roles) {
        return Optional.ofNullable(getUserDetail())
                .map(UserDetailsImpl::getAuthorities)
                .orElse(Collections.emptyList())
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> roles.stream().anyMatch(role -> role.getValue().equals(authority)));
    }
}
