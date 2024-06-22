package com.study8.sys.auth.entity;

import com.study8.core.entity.CoreEntity;
import com.study8.sys.auth.enumf.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AppRole
 * @Date: 2024-05-11
 * @Author: HuyNH
 * @Desc: Roles
 */
@Entity
@Table(name = "app_role")
@Getter
@Setter
@NoArgsConstructor
public class AppRole extends CoreEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
}
