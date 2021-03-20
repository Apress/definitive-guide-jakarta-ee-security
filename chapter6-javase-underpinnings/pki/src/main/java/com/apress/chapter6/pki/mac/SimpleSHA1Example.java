package com.apress.chapter6.pki.mac;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Calculates SHA-1 value of a message.
 * Uses a signum representation and a StringBuilder to manually construct the hashtext
 */
public class SimpleSHA1Example {

    public static void main(String[] args) throws Exception {

        String message = "This is a message";

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] messageDigest = md.digest(message.getBytes());

        // byte array to signum representation
        BigInteger signum = new BigInteger(1, messageDigest);
        // message digest to hex value
        StringBuilder hashtext = new StringBuilder(signum.toString(16));
        // add preceding 0s to make it 32 bit
        while (hashtext.length() < 32) hashtext.insert(0, "0");

        System.out.println("Message: " + message);
        System.out.println("Hashed message with SHA1: " + hashtext);
    }
}