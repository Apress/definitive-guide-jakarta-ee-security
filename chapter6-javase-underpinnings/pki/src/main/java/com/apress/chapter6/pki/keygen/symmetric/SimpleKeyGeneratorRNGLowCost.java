package com.apress.chapter6.pki.keygen.symmetric;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * A simple key generator for AES utilizing Bouncy Castle's capabilities and strengthened by a low-cost SecureRandom generator.
 *
 * SecureRandom is expensive to initialize (takes several milliseconds), therefore consider keeping the instance low if you are generating many keys.
 * This example demonstrates how to achieve this.
 */
public class SimpleKeyGeneratorRNGLowCost {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        SecureRandom keyRNG = new SecureRandom(); // strong random number generator (RNG)
        byte[] keyBytes = new byte[16];
        keyRNG.nextBytes(keyBytes);

        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        keygen.init(256, keyRNG); // 128, 192, or 256 are valid AES key sizes

        Key key = keygen.generateKey();
        System.out.println("Generated key value: " + Base64.getEncoder().encodeToString(key.getEncoded()));
    }
}
