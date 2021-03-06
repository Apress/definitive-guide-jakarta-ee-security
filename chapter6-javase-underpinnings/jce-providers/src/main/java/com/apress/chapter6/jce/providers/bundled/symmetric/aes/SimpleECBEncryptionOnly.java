package com.apress.chapter6.jce.providers.bundled.symmetric.aes;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Simple AES encryption in ECB mode.
 */
public class SimpleECBEncryptionOnly {

    public static void main(String[] args)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        String originalMessage = "This is a secret message";
        String encryptionKeyString =  "thisisa128bitkey"; // 128-bit key
        byte[] encryptionKeyBytes = encryptionKeyString.getBytes();

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(encryptionKeyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedMessageBytes = cipher.doFinal(originalMessage.getBytes());

        System.out.println("Original text: " + originalMessage);
        System.out.println("Encrypted text: " + Base64.getEncoder().encodeToString(encryptedMessageBytes));
    }
}
