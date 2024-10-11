package com.study8.sys.auth.repository;

import com.study8.sys.auth.entity.AppRole;
import com.study8.sys.auth.enumf.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * AppRoleRepository
 * @Date: 2024-05-11
 * @Author: HuyNH
 * @Desc: App_Role Repository
 */
@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByName(RoleEnum name);

    @Query(value = " SELECT " +
                "aur.role_id AS id, " +
                "ar.name AS name, " +
                "ar.created_id as created_id, " +
                "ar.created_date as created_date, " +
                "ar.deleted as deleted, " +
                "ar.updated_date as updated_date, " +
                "ar.updated_id as updated_id, " +
                "ar.deleted_date as deleted_date, " +
                "ar.deleted_id as deleted_id " +
            "FROM app_user_role aur " +
            "LEFT JOIN app_role ar " +
                "ON aur.role_id = ar.id " +
                "AND COALESCE(ar.deleted, 0) = 0 " +
            "WHERE aur.user_id = :userId", nativeQuery = true)
    Set<AppRole> findByUserId(@Param("userId") Long userId);
}
