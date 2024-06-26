package com.study8.sys.util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

public class PasswordUtils {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY = "aesEncryptionKey";
    private static final String IV = "encryptionIntVec";

    private static IvParameterSpec generateIv() {
        return new IvParameterSpec(IV.getBytes());
    }

    private static SecretKey generateKey() {
        return new SecretKeySpec(KEY.getBytes(), "AES");
    }

    public static String encrypt(String input) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, generateKey(), generateIv());
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, generateKey(), generateIv());
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }
}
