package com.apress.chapter6.jce.providers.bouncycastle.symmetric;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import java.security.*;
import java.util.Base64;

public class SimpleTwofishExample {

    public static void main(String[] args)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
            NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());

        String originalMessage = "This is a secret message";
        byte[] originalMessageBytes = originalMessage.getBytes();
        Key key = KeyGenerator.getInstance("Twofish", "BC").generateKey();

        Cipher cipher = Cipher.getInstance("Twofish/ECB/PKCS7Padding", "BC");

        System.out.println("Original: \t" + Base64.getEncoder().encodeToString(originalMessageBytes));

        // encryption
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(originalMessageBytes);
        System.out.println("Cipher: \t" + Base64.getEncoder().encodeToString(cipherText));

        // decryption
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedMessageBytes = cipher.doFinal(cipherText);
        System.out.println("decrypted : " + Base64.getEncoder().encodeToString(decryptedMessageBytes));

        System.out.println("Original and decrypted values " +
                (originalMessage.equals(new String(decryptedMessageBytes)) ? "" : "do not ") +
                "match");
    }
}
