package com.study8.sys.system.repository;

import com.study8.sys.system.entity.SystemInternalAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SystemInternalAssignmentRepository
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemInternalAssignmentRepository
 */
@Repository
public interface SystemInternalAssignmentRepository
        extends JpaRepository<SystemInternalAssignment, Long> {
    List<SystemInternalAssignment> findByGroupCodeAndActiveIsTrue(String groupCode);
}
