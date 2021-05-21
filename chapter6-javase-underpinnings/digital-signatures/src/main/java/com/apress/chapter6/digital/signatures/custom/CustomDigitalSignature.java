package com.apress.chapter6.digital.signatures.custom;

import com.apress.chapter6.digital.signatures.Utils;

import javax.crypto.Cipher;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.PrivateKey;

/**
 * This code example demonstrates the creation of a digital signature
 * using MessageDigest and Cipher low-level APIs, which allow us
 * to tailor the signing process to our needs.
 */
public class CustomDigitalSignature {

    public static void main(String[] args) throws Exception {

        PrivateKey privateKey = Utils.getPrivateKey();

        byte[] messageBytes = Utils.getResourceAsStream("message.txt").readAllBytes();
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] messageHash = messageDigest.digest(messageBytes);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] digitalSignature = cipher.doFinal(messageHash);

        Files.write(Utils.getTargetDirPath("/custom-digital-signature"), digitalSignature);
    }
}
