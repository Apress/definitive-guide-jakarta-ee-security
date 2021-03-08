package com.apress.chapter6.jce.providers.bundled.asymmetric;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;

/**
 * Simple asymmetric encryption example using RSA algorithm.
 * The transformation that is herewith demonstrated is RSA/None/OAEPWithSHA-256AndMGF1Padding,
 * as the old-fashioned RSA/ECB/PKCS1Padding is insecure is not considered secure anymore and should be avoided.
 */
public class SimpleRSAExample {

    public static void main(String[] args)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        String originalMessage = "This is a secret message";
        byte[] originalMessageBytes = originalMessage.getBytes();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // encryption
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding"); // this is a misnomer in JavaSE that has probably been copied
        // from the block cipher modes in the old Java versions. It does not allow multiple blocks to be encrypted,
        // which is what you would expect from ECB mode. That is, this is completely identical to "OAEPWithSHA-256AndMGF1Padding" -
        // but that algorithm is generally not provided with Java SE.
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