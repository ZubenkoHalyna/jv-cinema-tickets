package com.dev.cinema.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String HASH_ALGORITHM = "SHA-512";

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(salt);
            return toHexString(messageDigest.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hashPassword(String password, String salt) {
        return hashPassword(password, fromHexString(salt));
    }

    public static String toHexString(byte[] bytes) {
        return new BigInteger(bytes).toString(16);
    }

    private static byte[] fromHexString(String str) {
        return new BigInteger(str, 16).toByteArray();
    }
}
