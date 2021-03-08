package com.apress.chapter6.jce.providers.bundled.symmetric.aes;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SimpleCBCExampleWithIvParams {

    public static void main(String[] args)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
            InvalidAlgorithmParameterException {

        String originalMessage = "This is a secret message";
        byte[] originalMessageBytes = originalMessage.getBytes();
        byte[] encryptionKeyBytes =  "thisisa128bitkey".getBytes(); // 128-bit key
        SecretKey secretKey = new SecretKeySpec(encryptionKeyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        byte[] encryptedMessageBytes = cipher.doFinal(originalMessageBytes);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] decryptedMessageBytes = cipher.doFinal(encryptedMessageBytes);

        System.out.println("Original and decrypted values " +
                (originalMessage.equals(new String(decryptedMessageBytes)) ? "" : "do not ") +
                "match");
    }
}
