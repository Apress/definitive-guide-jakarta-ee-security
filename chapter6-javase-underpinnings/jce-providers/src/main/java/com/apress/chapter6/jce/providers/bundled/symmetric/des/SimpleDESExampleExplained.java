package com.apress.chapter6.jce.providers.bundled.symmetric.des;

import javax.crypto.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Simple DES example (CBC mode) explained.
 */
public class SimpleDESExampleExplained {

    public static void main(String[] args) throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException {

        String originalMessage;
        String cipherText;
        String decryptedText;

        /**
         *  Step 1. Generate a DES key using KeyGenerator
         */
        KeyGenerator keygen = KeyGenerator.getInstance("DES");
        SecretKey secretKey = keygen.generateKey();

        /**
         *  Step2. Create a Cipher by specifying the following parameters
         *          a. Algorithm name - here it is DES
         *          b. Mode - here it is CBC
         *          c. Padding - e.g. PKCS7Padding, PKCS5Padding, NoPadding
         *
         *  The combination of algorithm/mode/padding is also known as transformation
         *
         *  For block cipher use you need to to specify PKCS#5 padding.
         *  PKCS#7 is used in different places like in S/MIME.
         *  PKCS#5 padding is defined for 8-byte block sizes, while PKCS#7 padding would work for any block size from 1 to 255 bytes.
         *
         *  The bundled JCE provider's standard padding name is PKCS5Padding, not PKCS7Padding.
         *  Java is actually performing PKCS#7 padding, but in the JCA specification, PKCS5Padding is the name given.
         *  That is, for PKCS#5 padding you should consider using an external JCE provider like BouncyCastle.
         */
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // Specify the mode explicitly or most JCE providers will default to ECB.

        /**
         *  Step 3. Initialize the Cipher for encryption
         */
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);

        /**
         *  Step 4. Encrypt the Data
         *          1. Initialize the data. Here the data is of type String
         *          2. Convert the original message to bytes
         *          3. Encrypt the bytes using doFinal method
         */
        originalMessage = "This is a secret DES message";
        byte[] originalMessageBytes = originalMessage.getBytes();
        byte[] encryptedMessageBytes = cipher.doFinal(originalMessageBytes);
        cipherText = Base64.getEncoder().withoutPadding().encodeToString(encryptedMessageBytes);
        System.out.println("Ciphertext generated using DES/CBC/PKCS5Padding transformation is " + cipherText);

        /**
         *  Step 5. Decrypt the Data
         *          1. Initialize the Cipher for decryption
         *          2. Decrypt the cipher bytes using doFinal method
         */
        cipher.init(Cipher.DECRYPT_MODE,secretKey,cipher.getParameters());
        byte[] decryptedTextBytes = cipher.doFinal(encryptedMessageBytes);
        System.out.println("Decrypted text message is " + new String(decryptedTextBytes));
    }
}