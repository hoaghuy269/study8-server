package com.study8.sys.util;

import com.study8.sys.config.EncryptionConfig;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

/**
 * PasswordUtils
 * @Date: 2024-06-26
 * @Author: HuyNH
 * @Desc: Password Utils
 */
public class PasswordUtils {
    private  static final String AES = "AES";

    private static IvParameterSpec generateIv() {
        return new IvParameterSpec(EncryptionConfig.getIv().getBytes());
    }

    private static SecretKey generateKey() {
        return new SecretKeySpec(EncryptionConfig.getKey().getBytes(), AES);
    }

    public static String encrypt(String input) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(EncryptionConfig.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, generateKey(), generateIv());
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(EncryptionConfig.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, generateKey(), generateIv());
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }
}
