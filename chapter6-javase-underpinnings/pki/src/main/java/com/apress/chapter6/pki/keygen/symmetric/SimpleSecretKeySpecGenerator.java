package com.apress.chapter6.pki.keygen.symmetric;

import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Similar to SimpleKeyGeneratorRNGLowCost, but with a cleaner approach - demonstrates SecretKeySpec as an alternative to KeyGenerator
 */
public class SimpleSecretKeySpecGenerator {

    public static void main(String[] args) {

        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[16];
        random.nextBytes(keyBytes);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        System.out.println("Generated key value: " + Base64.getEncoder().encodeToString(key.getEncoded()));
    }
}
