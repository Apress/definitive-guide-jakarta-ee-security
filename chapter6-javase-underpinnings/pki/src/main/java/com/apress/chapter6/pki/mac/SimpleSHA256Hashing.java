package com.apress.chapter6.pki.mac;

import java.security.MessageDigest;
import java.util.Base64;

public class SimpleSHA256Hashing {

    public static void main(String[] args) throws Exception{

        String message = "This is a message";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(message.getBytes());
        String hashtext = Base64.getEncoder().encodeToString(hash);

        System.out.println("Message: " + message);
        System.out.println("Hashed message with SHA256: " + hashtext);
    }
}
