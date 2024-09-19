package com.study8.sys.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.entity.AppUser;
import com.study8.sys.auth.repository.AppUserRepository;
import com.study8.sys.auth.req.RegisterReq;
import com.study8.sys.auth.service.impl.AppUserServiceImpl;
import com.study8.sys.auth.validator.AppUserValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * AppUserServiceTest
 * @Date: 2024-08-13
 * @Author: HuyNH
 * @Desc: AppUser Service Test
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {
    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private AppUserServiceImpl appUserService;

    @Mock
    private AppUserValidator appUserValidator;

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

    @Test
    void whenGetListByPhoneNumber_shouldReturnListAppUser() {
        //1.Mock Data
        List<AppUser> appUserListMock = new ArrayList<>();
        AppUser appUser1 = new AppUser();
        appUser1.setId(1L);
        appUserListMock.add(appUser1);

        AppUserDto appUser1Dto = new AppUserDto();
        appUser1Dto.setId(1L);

        AppUser appUser2 = new AppUser();
        appUser2.setId(2L);
        appUserListMock.add(appUser2);

        AppUserDto appUser2Dto = new AppUserDto();
        appUser2Dto.setId(2L);

        when(appUserRepository.findByPhoneNumber("0911555888"))
                .thenReturn(appUserListMock);
        when(objectMapper.convertValue(appUser1, AppUserDto.class))
                .thenReturn(appUser1Dto);
        when(objectMapper.convertValue(appUser2, AppUserDto.class))
                .thenReturn(appUser2Dto);

        //2.Do test
        List<AppUserDto> appUserListActual = appUserService.getListByPhoneNumber("0911555888");
        assertEquals(appUserListMock.size(), appUserListActual.size());
        for (AppUserDto appUserActual : appUserListActual) {
            for (AppUser appUserMock : appUserListMock) {
                assertEquals(appUserActual.getCode(), appUserMock.getCode());
            }
        }
    }

    @Test
    void whenRegister_shouldReturnAppUser()
            throws CoreApplicationException {
        Locale locale = new Locale("vi");
        RegisterReq registerReq = new RegisterReq();
        registerReq.setUsername("test");
        registerReq.setPassword("test");
        registerReq.setPhoneNumber("0911567567");

        when(appUserValidator.validateBeforeRegister("test", "0911567567", locale)).thenReturn(true);
        appUserService.register(registerReq, locale);
        //TODO: Chưa viết xong unit test cho service register
    }
}
