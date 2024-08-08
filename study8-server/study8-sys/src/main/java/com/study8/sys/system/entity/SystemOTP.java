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

import java.time.LocalDateTime;

/**
 * SystemOTP
 * @Date: 2024-08-06
 * @Author: HuyNH
 * @Desc: Save OTP is generated by Study8
 */
@Entity
@Table(name = "system_otp")
@Getter
@Setter
@NoArgsConstructor
public class SystemOTP extends CoreEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "otp_type")
    private Integer otpType;

    @Column(name = "otp_code")
    private String otpCode;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "sent_date")
    private LocalDateTime sentDate;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
}
