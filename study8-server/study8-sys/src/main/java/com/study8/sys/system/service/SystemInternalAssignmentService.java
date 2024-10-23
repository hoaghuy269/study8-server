package com.study8.sys.system.service;

import com.study8.sys.system.dto.SystemInternalAssignmentDto;

import java.util.List;

/**
 * SystemInternalAssignmentService
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemInternalAssignmentService
 */
public interface SystemInternalAssignmentService {
    List<SystemInternalAssignmentDto> getListByGroupCode(String groupCode);
}
