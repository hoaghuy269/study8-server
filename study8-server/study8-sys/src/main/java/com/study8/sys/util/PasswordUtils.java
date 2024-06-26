package com.study8.sys.util;

import com.study8.sys.system.service.SystemConfigService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

/**
 * PasswordUtils
 * @Date: 2024-06-26
 * @Author: HuyNH
 * @Desc: Password Utils
 */
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordUtils {
    @Autowired
    SystemConfigService systemConfigService;

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH_BIT = 128;
    private static final String CRYPTO = "CRYPTO";
    private static final String SECRET_KEY = "SECRET_KEY";

    private static byte[] generateIv() {
        byte[] iv = new byte[IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

    private static SecretKey generateKey() {
//        String key = systemConfigService.getStringValue(SECRET_KEY, CRYPTO);
//        return new SecretKeySpec(key.getBytes(), "AES");
        return null;
    }

    public static String encrypt(String input) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        byte[] iv = generateIv();
        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        cipher.init(Cipher.ENCRYPT_MODE, generateKey(), parameterSpec);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        byte[] cipherTextWithIv = new byte[IV_LENGTH + cipherText.length];
        System.arraycopy(iv, 0, cipherTextWithIv, 0, IV_LENGTH);
        System.arraycopy(cipherText, 0, cipherTextWithIv, IV_LENGTH, cipherText.length);
        return Base64.getEncoder().encodeToString(cipherTextWithIv);
    }

    public static String decrypt(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        byte[] cipherTextWithIv = Base64.getDecoder().decode(cipherText);
        byte[] iv = new byte[IV_LENGTH];
        byte[] actualCipherText = new byte[cipherTextWithIv.length - IV_LENGTH];
        System.arraycopy(cipherTextWithIv, 0, iv, 0, IV_LENGTH);
        System.arraycopy(cipherTextWithIv, IV_LENGTH, actualCipherText, 0, actualCipherText.length);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        cipher.init(Cipher.DECRYPT_MODE, generateKey(), parameterSpec);
        byte[] plainText = cipher.doFinal(actualCipherText);
        return new String(plainText);
    }
}
