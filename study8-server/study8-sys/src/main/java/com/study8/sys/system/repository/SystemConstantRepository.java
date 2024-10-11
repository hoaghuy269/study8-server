package com.study8.sys.system.repository;

import com.study8.sys.system.entity.SystemConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SystemConstantRepository
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemConstantRepository
 */
@Repository
public interface SystemConstantRepository extends JpaRepository<SystemConstant, Long> {
    SystemConstant findByGroupCodeAndCodeAndActiveIsTrue(String groupCode, String code);
}
