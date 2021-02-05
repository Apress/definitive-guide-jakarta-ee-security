package com.apress.chapter6.digital.signatures.custom;

import com.apress.chapter6.digital.signatures.Utils;

import javax.crypto.Cipher;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.util.Arrays;

public class CustomDigitalSignatureVerification {

    public static void main(String[] args) throws Exception {

        PublicKey publicKey = Utils.getPublicKey();
        byte[] encryptedMessageHash = Utils.getResourceAsStream("custom-digital-signature.pfx").readAllBytes();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedMessageHash = cipher.doFinal(encryptedMessageHash);

        byte[] messageBytes = Utils.getResourceAsStream("message.txt").readAllBytes();
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] actualMessageHash = messageDigest.digest(messageBytes);

        boolean isVerified = Arrays.equals(decryptedMessageHash, actualMessageHash);
        System.out.println("Signature " + (isVerified ? "verified" : "not verified"));
    }
}