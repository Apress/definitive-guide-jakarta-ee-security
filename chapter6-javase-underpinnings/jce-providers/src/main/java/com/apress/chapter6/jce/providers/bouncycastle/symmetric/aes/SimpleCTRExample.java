package com.apress.chapter6.jce.providers.bouncycastle.symmetric.aes;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Simple AES encryption example in CTR mode with NoPadding.
 */
public class SimpleCTRExample {

    public static void main(String[] args)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
            InvalidAlgorithmParameterException {

        String originalMessage = "This is a secret message";
        String encryptionKeyString =  "thisisa128bitkey"; // 128-bit key
        byte[] encryptionKeyBytes = encryptionKeyString.getBytes();
        SecretKey secretKey = new SecretKeySpec(encryptionKeyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);

        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", new BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        byte[] encryptedMessageBytes = cipher.doFinal(originalMessage.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] decryptedMessageBytes = cipher.doFinal(encryptedMessageBytes);

        System.out.println("Original: " + Base64.getEncoder().encodeToString(originalMessage.getBytes()));
        System.out.println("Cipher: " + Base64.getEncoder().encodeToString(encryptedMessageBytes));
        System.out.println("Decrypted: " + Base64.getEncoder().encodeToString(decryptedMessageBytes));
        System.out.println("Original and decrypted values " +
                (originalMessage.equals(new String(decryptedMessageBytes)) ? "" : "do not ") +
                "match");
    }
}
