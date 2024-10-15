package com.study8.sys.auth.repository;

import com.study8.sys.auth.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AppUserRepository
 * @Date: 2024-05-11
 * @Author: HuyNH
 * @Desc: AppUser repository
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    @Query("SELECT u FROM AppUser u " +
            "WHERE u.username = :username " +
                "AND COALESCE(u.deleted, 0) = 0")
    AppUser findByUsername(@Param("username") String username);

    @Query("SELECT u FROM AppUser u " +
            "WHERE u.phoneNumber = :phoneNumber " +
                "AND COALESCE(u.deleted, 0) = 0")
    List<AppUser> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT u FROM AppUser u " +
            "WHERE u.email = :email " +
            "AND COALESCE(u.deleted, 0) = 0")
    List<AppUser> findByEmail(@Param("email") String email);

    AppUser findByResetPassword(String resetPassword);
}
