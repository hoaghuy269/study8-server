package com.study8.sys.system.entity;

import com.study8.core.entity.CoreEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SystemInternalAssignment
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemInternalAssignment
 */
@Entity
@Table(name = "system_internal_assignment")
@Getter
@Setter
@NoArgsConstructor
public class SystemInternalAssignment
        extends CoreEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_code")
    private String groupCode;

    @Column(name = "code")
    private String code;

    @Column(name = "assignee_id")
    private Long assigneeId;

    @Column(name = "active")
    private Boolean active;
}
