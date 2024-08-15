package com.study8.sys.system.dto;

import com.study8.core.dto.CoreDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * SystemOTPDto
 * @Date: 2024-08-06
 * @Author: HuyNH
 * @Desc: SystemOTP Dto
 */
@NoArgsConstructor
@Getter
@Setter
public class SystemOTPDto extends CoreDto {
    private Long id;
    private Long userId;
    private Integer otpType;
    private String otpCode;
    private Boolean active;
    private LocalDateTime sentDate;
    private LocalDateTime expiryDate;
    private LocalDateTime verificationDate;

    public SystemOTPDto(Long id, Long userId,
                        Integer otpType, String otpCode,
                        Boolean active, LocalDateTime sentDate,
                        LocalDateTime expiryDate, LocalDateTime verificationDate) {
        this.id = id;
        this.userId = userId;
        this.otpType = otpType;
        this.otpCode = otpCode;
        this.active = active;
        this.sentDate = sentDate;
        this.expiryDate = expiryDate;
        this.verificationDate = verificationDate;
    }
}
