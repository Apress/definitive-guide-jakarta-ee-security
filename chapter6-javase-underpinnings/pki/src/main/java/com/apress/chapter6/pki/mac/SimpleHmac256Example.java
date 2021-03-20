package com.apress.chapter6.pki.mac;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * MAC using HmacSHA256 algorithm
 */
public class SimpleHmac256Example {

    public static void main(String[] args) throws Exception {

        String message = "This is a secret message";
        KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
        keygen.init(256);
        SecretKey hmacKey = keygen.generateKey();

        Mac mac = Mac.getInstance(keygen.getAlgorithm());
        mac.init(hmacKey);
        byte[] hmac256 = mac.doFinal(message.getBytes());

        System.out.println("Computed MAC on sender side: " + Base64.getEncoder().encodeToString(hmac256));
    }
}
