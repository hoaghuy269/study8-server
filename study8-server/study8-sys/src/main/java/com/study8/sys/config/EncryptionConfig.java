package com.study8.sys.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * EncryptionConfig
 * @Date: 2024-06-27
 * @Author: HuyNH
 * @Desc: Config encrypt password
 */
@Configuration
public class EncryptionConfig {
    @Value("${encryption.algorithm}")
    private String algorithm;

    @Value("${encryption.key}")
    private String key;

    @Value("${encryption.iv}")
    private String iv;

    private static String staticAlgorithm;
    private static String staticKey;
    private static String staticIv;

    @PostConstruct
    public void init() {
        staticAlgorithm = this.algorithm;
        staticKey = this.key;
        staticIv = this.iv;
    }

    public static String getAlgorithm() {
        return staticAlgorithm;
    }

    public static String getKey() {
        return staticKey;
    }

    public static String getIv() {
        return staticIv;
    }
}
