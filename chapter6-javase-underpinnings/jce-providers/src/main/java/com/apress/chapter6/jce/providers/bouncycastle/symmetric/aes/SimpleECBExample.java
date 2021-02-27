package com.apress.chapter6.jce.providers.bouncycastle.symmetric.aes;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Simple AES encryption example in ECB mode with PKCS7Padding.
 */
public class SimpleECBExample {

    public static void main(String[] args)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        String originalMessage = "This is a secret message";
        String encryptionKeyString =  "thisisa128bitkey"; // 128-bit key
        byte[] encryptionKeyBytes = encryptionKeyString.getBytes();

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", new BouncyCastleProvider());
        SecretKey secretKey = new SecretKeySpec(encryptionKeyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedMessageBytes = cipher.doFinal(originalMessage.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedMessageBytes = cipher.doFinal(encryptedMessageBytes);

        System.out.println("Original: " + Base64.getEncoder().encodeToString(originalMessage.getBytes()));
        System.out.println("Cipher: " + Base64.getEncoder().encodeToString(encryptedMessageBytes));
        System.out.println("Decrypted: " + Base64.getEncoder().encodeToString(decryptedMessageBytes));
        System.out.println("Original and decrypted values " +
                (originalMessage.equals(new String(decryptedMessageBytes)) ? "" : "do not ") +
                "match");
    }
}
