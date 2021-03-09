package com.apress.chapter6.jce.providers.bundled.symmetric.aes;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Simple AES example (CBC mode) explained.
 */
public class SimpleAESExampleExplained {

    public static void main(String[] args)
            throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException {

        String originalMessage;
        String cipherText;
        String decryptedText;

        /**
         * Step 1. Generate an AES key using KeyGenerator.
         * Initialize the keysize to 128 bits (16 bytes).
         */
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        keygen.init(128);
        SecretKey secretKey = keygen.generateKey();

        /**
         * Step 2. Generate an Initialization Vector (IV)
         *      a. Use SecureRandom to generate random bits
         *         The size of the IV matches the block size of the cipher (128 bits for AES)
         *      b. Construct the appropriate IvParameterSpec object to be passed to Cipher's init() method
         */
        final int AES_KEY_LENGTH = 128; // change this as desired for the security level you want (128, 192, 256)
        byte[] iv = new byte[AES_KEY_LENGTH / 8]; // save the IV bytes or send it in plaintext with the encrypted data (so you can decrypt it later)
        SecureRandom prng = new SecureRandom();
        prng.nextBytes(iv);

        /**
         * Step 3. Create a Cipher by specifying the following parameters
         *      a. Algorithm name - here it is AES
         *      b. Mode - here it is CBC
         *      c. Padding - e.g. PKCS7Padding, PKCS5Padding, NoPadding
         *
         * The combination of algorithm/mode/padding is also known as transformation
         *
         * For block cipher use you need to to specify PKCS#5 padding.
         * PKCS#7 is used in different places like in S/MIME.
         * PKCS#5 padding is defined for 8-byte block sizes, while PKCS#7 padding would work for any block size from 1 to 255 bytes.
         *
         * The bundled JCE provider's standard padding name is PKCS5Padding, not PKCS7Padding.
         * Java is actually performing PKCS#7 padding, but in the JCA specification, PKCS5Padding is the name given.
         * That is, for PKCS#5 padding you should consider using an external JCE provider like BouncyCastle.
         */
        Cipher encryptionCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // Specify the mode explicitly or most JCE providers will default to ECB.

        /**
         * Step 4. Initialize the Cipher for encryption
         */
        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

        /**
         * Step 5. Encrypt the data
         *      a. Initialize the data. Here the data is of type String
         *      b. Convert the original message to bytes
         *      c. Encrypt the bytes using doFinal method
         */
        originalMessage = "This is a secret AES message";
        byte[] originalMessageBytes = originalMessage.getBytes();
        byte[] encryptedMessageBytes = encryptionCipher.doFinal(originalMessageBytes);
        cipherText = Base64.getEncoder().withoutPadding().encodeToString(encryptedMessageBytes);
        System.out.println("Ciphertext generated using AES/CBC/PKCS5Padding transformation is " + cipherText);

        /**
         * Step 6. Decrypt the Data
         *      a. Initialize a new instance of Cipher for decryption (normally we shouldn't reuse the same object)
         *         Be sure to obtain the same IV bytes for CBC mode.
         *      b. Decrypt the cipher bytes using doFinal method
         */
        Cipher decryptionCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] decryptedTextBytes = decryptionCipher.doFinal(encryptedMessageBytes);
        System.out.println("Decrypted text message is " + new String(decryptedTextBytes));
    }
}