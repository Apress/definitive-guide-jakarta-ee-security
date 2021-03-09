package com.apress.chapter6.jce.providers.bouncycastle.asymmetric;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.OAEPEncoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;

import java.math.BigInteger;
import java.security.*;
import java.util.Base64;

/**
 * Simple asymmetric encryption example for RSA algorithm utilizing BouncyCastle's light-weight API
 */
public class SimpleRSAExampleWithBC {

    public static void main(String[] args) throws InvalidCipherTextException {

        String originalMessage = "This is a secret message";
        byte[] originalMessageBytes = originalMessage.getBytes();

        RSAKeyPairGenerator keyPairGenerator = new RSAKeyPairGenerator();
        keyPairGenerator.init(new RSAKeyGenerationParameters(new BigInteger("10001", 16), new SecureRandom(), 2048, 80));
        AsymmetricCipherKeyPair keyPair = keyPairGenerator.generateKeyPair();

        // encryption
        AsymmetricBlockCipher cipher = new RSAEngine();
        cipher = new OAEPEncoding(cipher);
        cipher.init(true, keyPair.getPublic());

        System.out.println("Plain: " + Base64.getEncoder().encodeToString(originalMessageBytes));

        byte[] cipherText = cipher.processBlock(originalMessageBytes, 0, originalMessageBytes.length);
        System.out.println("Cipher: " + Base64.getEncoder().encodeToString(cipherText));

        // decryption
        cipher.init(false, keyPair.getPrivate());
        byte[] decryptedMessageBytes = cipher.processBlock(cipherText, 0, cipherText.length);
        System.out.println("Decrypted: " + Base64.getEncoder().encodeToString(decryptedMessageBytes));

        System.out.println("Original and decrypted values " +
                (originalMessage.equals(new String(decryptedMessageBytes)) ? "" : "do not ") +
                "match");
    }
}