package com.apress.chapter6.pki.keygen.symmetric;

import org.bouncycastle.crypto.params.KeyParameter;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * A simple key generator for AES utilizing Bouncy Castle's capabilities.
 */
public class SimpleKeyGeneratorWithBC {

    private static final int AES_KEY_SIZE = 256;

    public static void main(String[] args) {

        SecureRandom keyRNG = new SecureRandom();
        byte[] keyBytes = new byte[AES_KEY_SIZE / Byte.SIZE];
        keyRNG.nextBytes(keyBytes);

        KeyParameter keyParameter = new KeyParameter(keyBytes);
        System.out.println("Generated key value: " + Base64.getEncoder().encodeToString(keyParameter.getKey()));
    }
}
