package com.apress.chapter6.jce.providers.bundled.aes;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

/**
 * Simple AES encryption example in CTR mode.
 */
public class SimpleCTRExample {

    public static void main(String[] args)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
            InvalidAlgorithmParameterException {

        String originalMessage = "This is a secret message";
        byte[] originalMessageBytes = originalMessage.getBytes();

        // generate a key
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[256 / 8]; // key can be 128, 192 or 256 bit
        secureRandom.nextBytes(key);
        // generate a nonce. You can also use an ever-increasing counter, which is more secure. Never use a nonce in production!
        byte[] nonce = new byte[96 / 8];
        secureRandom.nextBytes(nonce);

        // IV is a nonce followed by a counter (starting at 0). The IV is always 128 bit long.
        // IV in hex looks for example: a2591afec0b2575c50943f2100000000
        //                              |nonce                  |counter
        byte[] iv = new byte[128 / 8];
        System.arraycopy(nonce, 0, iv, 0, nonce.length);
        // No need to explicitly set the counter to 0, as Java arrays are initialized with 0 anyway

        Key keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");

        System.out.println("Original: " + Base64.getEncoder().encodeToString(originalMessageBytes));

        // encryption
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] ciphertext = cipher.doFinal(originalMessageBytes);
        System.out.println("Cipher: " + Base64.getEncoder().encodeToString(ciphertext));

        // decryption
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decryptText = cipher.doFinal(ciphertext);
        System.out.println("Decrypted: " + Base64.getEncoder().encodeToString(decryptText));

        System.out.println("Original and decrypted values " +
                (originalMessage.equals(new String(decryptText)) ? "" : "do not ") +
                "match");
    }
}