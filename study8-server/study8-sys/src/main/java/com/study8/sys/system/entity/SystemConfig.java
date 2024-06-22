package com.study8.sys.system.entity;

import com.study8.core.entity.CoreEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SystemConfig
 * @Date: 2024-05-11
 * @Author: HuyNH
 * @Desc: System Config
 */
@Entity
@Table(name = "system_config")
@Getter
@Setter
@NoArgsConstructor
public class SystemConfig extends CoreEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_code")
    private String groupCode;

    @Column(name = "code")
    private String code;

    @Column(name = "value")
    private String value;
}
