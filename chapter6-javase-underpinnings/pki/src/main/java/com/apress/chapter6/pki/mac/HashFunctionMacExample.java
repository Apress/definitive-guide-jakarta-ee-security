package com.apress.chapter6.pki.mac;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

/**
 * Simple AES encryption in CTR mode demonstrating the use of a hash function (HmacSHA256 in this case) to compute a MAC.
 */
public class HashFunctionMacExample {

    public static void main(String[] args) throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        String plainText = "This is a message";
        SecureRandom random = new SecureRandom();
        IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);

        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        keygen.init(256, random);
        Key key = keygen.generateKey();

        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");

        Mac mac = Mac.getInstance("HmacSHA256");
        byte[] macKeyBytes = new byte[8];
        Key hmacSHA256 = new SecretKeySpec(macKeyBytes, "HmacSHA256");
        
        System.out.println("Plain text: " + plainText);
        
        // encryption
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] ciphertext = new byte[cipher.getOutputSize(plainText.length() + mac.getMacLength())];
        int ctLength = cipher.update(plainText.getBytes(), 0, plainText.length(), ciphertext, 0);

        mac.init(hmacSHA256);
        mac.update(plainText.getBytes());

        ctLength += cipher.doFinal(mac.doFinal(), 0, mac.getMacLength(), ciphertext, ctLength);

        // decryption
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decryptedTextBytes = cipher.doFinal(ciphertext, 0, ctLength);
        int messageLength = decryptedTextBytes.length - mac.getMacLength();
        
        mac.init(hmacSHA256);
        mac.update(decryptedTextBytes, 0, messageLength);
        
        byte[] messageHash = new byte[mac.getMacLength()];
        System.arraycopy(decryptedTextBytes, messageLength, messageHash, 0, messageHash.length);
        
        System.out.println("Ciphertext: " + Base64.getEncoder().encodeToString(decryptedTextBytes));
        System.out.println("Message is " + (MessageDigest.isEqual(mac.doFinal(), messageHash) ? "authenticated" : "could not be authenticated"));
    }
}