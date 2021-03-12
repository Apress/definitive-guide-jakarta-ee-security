package com.apress.chapter6.pki.keygen.symmetric;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * This example demonstrates how to convert a SecretKey to String and vice-versa.
 */
public class SecretKeyToStringExample {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        /**
         * SecretKey to String
         */

        // generate a new key
        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
        System.out.println("Generated key: " + secretKey);

        // get base64 encoded version of the key
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Encrypted key: " + encodedKey);


        /**
         * String to SecretKey
         */

        // decode the base64 encoded string
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        System.out.println("Decoded key: " + Base64.getEncoder().encodeToString(decodedKey));
        // rebuild key using SecretKeySpec
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        System.out.println("Original key: " + originalKey);
    }
}