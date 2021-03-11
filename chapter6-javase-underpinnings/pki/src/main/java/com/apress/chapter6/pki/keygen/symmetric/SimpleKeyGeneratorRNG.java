package com.apress.chapter6.pki.keygen.symmetric;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * A simple key generator for AES using JCA and strengthened by a SecureRandom generator.
 */
public class SimpleKeyGeneratorRNG {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecureRandom keyRNG = new SecureRandom(); // strong random number generator (RNG)
        keygen.init(256, keyRNG); // 128, 192, or 256 are valid AES key sizes

        Key key = keygen.generateKey();
        System.out.println("Generated key value: " + Base64.getEncoder().encodeToString(key.getEncoded()));
    }
}
