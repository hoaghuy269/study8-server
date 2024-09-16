package com.study8.sys.auth.repository;

import com.study8.sys.auth.entity.AppRole;
import com.study8.sys.auth.enumf.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AppRoleRepository
 * @Date: 2024-05-11
 * @Author: HuyNH
 * @Desc: App_Role Repository
 */
@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByName(RoleEnum name);
}
