package com.study8.sys.auth.repository;

import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * AppUserRepository
 * @Date: 2024-05-11
 * @Author: HuyNH
 * @Desc: AppUser repository
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    @Query("SELECT new com.study8.sys.auth.dto.AppUserDto(u.code, u.username, u.password, u.active, u.createdDate, u.createdId, u.deleted, u.deletedDate, u.deletedId) " +
            "FROM AppUser u WHERE u.username = :username")
    AppUserDto findByUsername(@Param("username") String username);

    @Query("SELECT new com.study8.sys.auth.dto.AppUserDto(u.code, u.username, u.password, u.active, u.createdDate, u.createdId, u.deleted, u.deletedDate, u.deletedId) " +
            "FROM AppUser u WHERE u.phoneNumber = :phoneNumber")
    AppUserDto findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
