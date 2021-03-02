package com.apress.chapter6.jce.providers.bouncycastle.asymmetric;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Simple asymmetric encryption example for RSA algorithm utilizing BouncyCastle capabilities.
 */
public class SimpleRSAExample {

    public static void main(String[] args)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        String originalMessage = "This is a secret message";
        byte[] originalMessageBytes = originalMessage.getBytes();

        // generate a random key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", new BouncyCastleProvider());
        keyPairGenerator.initialize(4096);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // encryption
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());

        System.out.println("Plain: " + Base64.getEncoder().encodeToString(originalMessageBytes));

        byte[] cipherText = cipher.doFinal(originalMessageBytes);
        System.out.println("Cipher: " + Base64.getEncoder().encodeToString(cipherText));

        // decryption
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        byte[] decryptedMessageBytes = cipher.doFinal(cipherText);
        System.out.println("Decrypted: " + Base64.getEncoder().encodeToString(decryptedMessageBytes));

        System.out.println("Original and decrypted values " +
                (originalMessage.equals(new String(decryptedMessageBytes)) ? "" : "do not ") +
                "match");
    }
}