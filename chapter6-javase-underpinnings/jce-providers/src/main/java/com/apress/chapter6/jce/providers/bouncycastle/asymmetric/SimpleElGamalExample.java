package com.apress.chapter6.jce.providers.bouncycastle.asymmetric;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

/**
 * Simple El Gamal example with random key generation.
 */
public class SimpleElGamalExample {

    public static void main(String[] args) throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        String plainText = "This is a secret message";
        byte[] plainTextBytes = plainText.getBytes();

        // Side A generates a key pair and shares their public key with Side B. Side A's private key is kept secret.
        KeyPairGenerator generator = KeyPairGenerator.getInstance("ElGamal", "BC");
        generator.initialize(256);
        KeyPair keyPair = generator.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();

        System.out.println("Plain: " + Base64.getEncoder().encodeToString(plainTextBytes));
        
        // Side B encrypts a message under Side A's public key.
        Cipher cipher = Cipher.getInstance("ElGamal/None/NoPadding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherText = cipher.doFinal(plainTextBytes);
        System.out.println("Cipher: " + Base64.getEncoder().encodeToString(cipherText));
        
        // Side A decrypts the ciphertext with their private key
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessageBytes = cipher.doFinal(cipherText);
        System.out.println("Decrypted: " + Base64.getEncoder().encodeToString(decryptedMessageBytes));

        System.out.println("Original and decrypted values " +
                (plainText.equals(new String(decryptedMessageBytes)) ? "" : "do not ") +
                "match");
    }
}