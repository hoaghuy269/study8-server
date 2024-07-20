package com.study8.sys.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.entity.AppUser;
import com.study8.sys.auth.service.AppUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserDetailsServiceImpl
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: User Details Service Impl
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserDto appUserDto = appUserService.getByUsername(username);
        if (ObjectUtils.isEmpty(appUserDto)) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        AppUser appUser = objectMapper.convertValue(appUserDto, AppUser.class);
        return UserDetailsImpl.build(appUser);
    }

}
