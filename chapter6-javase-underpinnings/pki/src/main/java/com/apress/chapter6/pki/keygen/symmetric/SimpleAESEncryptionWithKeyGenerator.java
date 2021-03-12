package com.apress.chapter6.pki.keygen.symmetric;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Utilize KeyGenerator to generate a symmetric key to be used for AES encryption
 */
public class SimpleAESEncryptionWithKeyGenerator {

    public static void main(String[] args)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String originalMessage = "This is a secret message.";

        // Generate the key first
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        keygen.init(256);  // Key size - AES 128 or 192 or 256
        Key key = keygen.generateKey();

        // Create Cipher instance and initialize it to encrytion mode
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedMessageBytes = cipher.doFinal(originalMessage.getBytes());

        // Reinitialize the Cipher to decryption mode
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedMessageBytes = cipher.doFinal(encryptedMessageBytes);

        System.out.println("Original and decrypted values " +
                (originalMessage.equals(new String(decryptedMessageBytes)) ? "" : "do not ") +
                "match");
    }
}