package com.study8.sys.system.repository;

import com.study8.sys.system.entity.SystemErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SystemErrorLogRepository
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemErrorLogRepository
 */
@Repository
public interface SystemErrorLogRepository extends JpaRepository<SystemErrorLog, Long> {
}
