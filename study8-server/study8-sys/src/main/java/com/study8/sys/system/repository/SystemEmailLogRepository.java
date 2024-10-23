package com.study8.sys.system.repository;

import com.study8.sys.system.entity.SystemEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * SystemEmailLogRepository
 * @Date: 2024-10-09
 * @Author: HuyNH
 * @Desc: SystemEmailLogRepository
 */
public interface SystemEmailLogRepository extends JpaRepository<SystemEmailLog, Long> {
}
