package com.study8.sys.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.entity.AppUser;
import com.study8.sys.auth.repository.AppUserRepository;
import com.study8.sys.auth.service.impl.AppUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void whenGetByUsername_shouldReturnAppUser() {
        AppUser appUserMock = new AppUser();
        appUserMock.setId(1L);
        appUserMock.setUsername("admin");

        AppUserDto appUserDtoMock = new AppUserDto();
        appUserDtoMock.setId(1L);
        appUserDtoMock.setUsername("admin");

        when(appUserRepository.findByUsername("admin")).thenReturn(appUserMock);
        when(objectMapper.convertValue(appUserMock, AppUserDto.class)).thenReturn(appUserDtoMock);

        AppUserDto appUserDtoActual = appUserService.getByUsername("admin");

        assertEquals(appUserDtoActual.getId(), appUserMock.getId());
    }

    @Test
    void whenGetByUsername_shouldReturnNull() {
        when(appUserRepository.findByUsername("unknownUser")).thenReturn(null);

        AppUserDto appUserDtoActual = appUserService.getByUsername("unknownUser");

        assertNull(appUserDtoActual);
    }
}
