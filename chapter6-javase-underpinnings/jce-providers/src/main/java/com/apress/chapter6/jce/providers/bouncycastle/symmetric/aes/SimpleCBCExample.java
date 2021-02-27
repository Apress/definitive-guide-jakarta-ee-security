package com.apress.chapter6.jce.providers.bouncycastle.symmetric.aes;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Simple AES encryption example in CBC mode with PKCS7Padding.
 */
public class SimpleCBCExample {

    public static void main(String[] args)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
            InvalidAlgorithmParameterException {

        String originalMessage = "This is a secret message";
        byte[] originalMessageBytes = originalMessage.getBytes();
        byte[] encryptionKeyBytes =  "thisisa128bitkey".getBytes(); // 128-bit key

        SecretKeySpec   key = new SecretKeySpec(encryptionKeyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", new BouncyCastleProvider());

        System.out.println("Plain: " + Base64.getEncoder().encodeToString(originalMessageBytes));

        // encryption
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] cipherText = cipher.doFinal(originalMessageBytes);
        System.out.println("Cipher: " + Base64.getEncoder().encodeToString(cipherText));

        // decryption
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decryptedMessageBytes = cipher.doFinal(cipherText);
        System.out.println("Decrypted : " + Base64.getEncoder().encodeToString(decryptedMessageBytes));

        System.out.println("Original and decrypted values " +
                (originalMessage.equals(new String(decryptedMessageBytes)) ? "" : "do not ") +
                "match");
    }
}