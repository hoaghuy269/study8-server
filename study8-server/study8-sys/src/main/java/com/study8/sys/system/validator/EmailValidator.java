package com.study8.sys.system.validator;

import com.study8.sys.system.dto.SendEmailDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * EmailValidator
 * @Date: 2024-06-28
 * @Author: HuyNH
 * @Desc: Email Validator
 */
@Component
public class EmailValidator {
    public Boolean isDataValid(SendEmailDto sendEmailDto) {
        boolean result = true;
        if (CollectionUtils.isEmpty(sendEmailDto.getTo())
                || StringUtils.isEmpty(sendEmailDto.getSubject())
                || StringUtils.isEmpty(sendEmailDto.getTemplateCode())) {
            result = false;
            return result;
        }
        return result;
    }
}
