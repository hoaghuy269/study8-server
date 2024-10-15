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
 * SystemConstant
 * @Date: 2024-10-01
 * @Author: HuyNH
 * @Desc: SystemConstant
 */
@Entity
@Table(name = "system_constant")
@Getter
@Setter
@NoArgsConstructor
public class SystemConstant extends CoreEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "group_code")
    private String groupCode;

    @Column(name = "code")
    private String code;

    @Column(name = "kind")
    private String kind;

    @Column(name = "name")
    private String name;

    @Column(name = "lang")
    private String lang;

    @Column(name = "active")
    private Boolean active;
}
