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
    @Query("SELECT new com.study8.sys.system.dto.SystemConfigDto(sc.groupCode, sc.code, sc.value, sc.createdDate, sc.createdId, sc.deleted, sc.deletedDate, sc.deletedId) " +
            "FROM SystemConfig sc WHERE sc.code = :code " +
                "AND sc.groupCode = :groupCode")
    SystemConfigDto findByCodeAndGroupCode(@Param("code") String code,
                                           @Param("groupCode") String groupCode);

    @Query("SELECT new com.study8.sys.system.dto.SystemConfigDto(sc.groupCode, sc.code, sc.value, sc.createdDate, sc.createdId, sc.deleted, sc.deletedDate, sc.deletedId) " +
            "FROM SystemConfig sc WHERE sc.groupCode = :groupCode")
    List<SystemConfigDto> findListByGroupCode(@Param("groupCode") String groupCode);
}
