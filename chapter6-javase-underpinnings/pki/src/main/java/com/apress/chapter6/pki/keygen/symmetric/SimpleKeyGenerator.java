package com.apress.chapter6.pki.keygen.symmetric;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * A simple key generator for AES using JCA.
 */
public class SimpleKeyGenerator {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        keygen.init(256); // 128, 192, or 256 are valid AES key sizes
        Key key = keygen.generateKey();

        System.out.println("Generated key value: " + Base64.getEncoder().encodeToString(key.getEncoded()));
    }
}
