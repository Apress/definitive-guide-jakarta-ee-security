package com.apress.chapter6.pki.mac;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * Similar to SimpleHmac256Example.java, but with a verification of the re-computed MAC
 */
public class TwoSideHmac256Example {

    public static void main(String[] args) throws Exception {

        String message = "This is a message";
        KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
        keygen.init(256);
        SecretKey hmacKey = keygen.generateKey(); // shared key to be used by both parties

        System.out.println("Original message: " + message);

        Mac sMac = Mac.getInstance(keygen.getAlgorithm());
        sMac.init(hmacKey);
        byte[] senderMac = sMac.doFinal(message.getBytes());
        System.out.println("Computed MAC on sender side: " + Base64.getEncoder().encodeToString(senderMac));

        Mac rMac = Mac.getInstance(keygen.getAlgorithm());
        rMac.init(hmacKey);
        byte[] receiverMac = rMac.doFinal(message.getBytes());
        System.out.println("Recomputed MAC on receiver side: " + Base64.getEncoder().encodeToString(receiverMac));

        System.out.println("Message is " + (MessageDigest.isEqual(receiverMac, senderMac) ? "authenticated" : "could not be authenticated"));
    }
}
