package com.study8.sys.auth.service;

import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.repository.AppUserRepository;
import com.study8.sys.auth.service.impl.AppUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * AppUserServiceTest
 * @Date: 2024-08-13
 * @Author: HuyNH
 * @Desc: AppUser Service Test
 */
@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {
    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private AppUserServiceImpl appUserService;

    @Test
    void whenGetByUsername_shouldReturnAppUser() {
        String username = "admin";
        AppUserDto expectedUser = new AppUserDto();
        expectedUser.setUsername(username);

        when(appUserRepository.findByUsername(anyString())).thenReturn(expectedUser);

        AppUserDto actualUser = appUserService.getByUsername(username);

        assertEquals(expectedUser, actualUser);
    }
}
