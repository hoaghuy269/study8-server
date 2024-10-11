package com.study8.sys.system.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study8.sys.system.dto.SystemInternalAssignmentDto;
import com.study8.sys.system.entity.SystemInternalAssignment;
import com.study8.sys.system.repository.SystemInternalAssignmentRepository;
import com.study8.sys.system.service.SystemInternalAssignmentService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * SystemInternalAssignmentServiceImpl
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemInternalAssignmentServiceImpl
 */
@Service
public class SystemInternalAssignmentServiceImpl
        implements SystemInternalAssignmentService {
    @Autowired
    private SystemInternalAssignmentRepository systemInternalAssignmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<SystemInternalAssignmentDto> getListByGroupCode(String groupCode) {
        List<SystemInternalAssignmentDto> result = new ArrayList<>();
        List<SystemInternalAssignment> systemInternalAssignmentList = systemInternalAssignmentRepository
                .findByGroupCodeAndActiveIsTrue(
                        groupCode);
        if (CollectionUtils.isNotEmpty(
                systemInternalAssignmentList)) {
            for (SystemInternalAssignment systemInternalAssignment : systemInternalAssignmentList) {
                result.add(objectMapper.convertValue(
                        systemInternalAssignment,
                        SystemInternalAssignmentDto.class));
            }
        }
        return result;
    }
}
