package com.study8.sys.system.repository;

import com.study8.sys.system.dto.SystemConfigDto;
import com.study8.sys.system.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SystemConfigRepository
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: System Config Repository
 */
@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
    @Query("SELECT sc FROM SystemConfig sc " +
            "WHERE sc.code = :code " +
                "AND sc.groupCode = :groupCode")
    SystemConfig findByCodeAndGroupCode(@Param("code") String code,
                                           @Param("groupCode") String groupCode);

    @Query("SELECT sc FROM SystemConfig sc " +
            "WHERE sc.groupCode = :groupCode")
    List<SystemConfig> findListByGroupCode(@Param("groupCode") String groupCode);
}
