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
    @Query("SELECT new com.study8.sys.auth.dto.AppUserDto(u.id, u.code, u.username, u.password, u.email, u.phoneNumber, u.active, " +
                "u.createdDate, u.createdId, u.deleted, u.deletedDate, u.deletedId, u.emailVerified, u.phoneNumberVerified) " +
            "FROM AppUser u WHERE u.username = :username " +
                "AND COALESCE(u.deleted, 0) = 0")
    AppUserDto findByUsername(@Param("username") String username);

    @Query("SELECT new com.study8.sys.auth.dto.AppUserDto(u.id, u.code, u.username, u.password, u.email, u.phoneNumber, u.active, " +
                "u.createdDate, u.createdId, u.deleted, u.deletedDate, u.deletedId, u.emailVerified, u.phoneNumberVerified) " +
            "FROM AppUser u WHERE u.phoneNumber = :phoneNumber " +
                "AND COALESCE(u.deleted, 0) = 0")
    AppUserDto findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
