package com.apress.chapter6.jce.providers.bundled.asymmetric;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Simple example that demonstrates a hybrid encryption approach using RSA in combination with AES.
 *
 * Steps
 * 1. Encrypt the large message using AES
 * 2. Encrypt the AES key is using the RSA
 * 3. Encrypted AES message and RSA encrypted AES key are sent across.
 * 4. Decrypt AES key is using RSA public key
 * 5. Decrypt message is with AES key
 */
public class RSAEncryptionWithAES {

    public static void main(String[] args) throws Exception {
        String plainText = "This is supposed to be a large message";

        PublicKey publicKey = RSAUtils.getPublicKey("id_rsa.pub");
        PrivateKey privateKey = RSAUtils.getPrivateKey("id_rsa.key");

        // First create an AES key
        String secretAESKeyString = getSecretAESKeyAsString();

        // Encrypt data with AES key
        String encryptedText = encryptTextUsingAES(plainText, secretAESKeyString);

        // Encrypt AES key with RSA Private Key
        String encryptedAESKeyString = encryptAESKey(secretAESKeyString, privateKey);

        /** Now let's see what happens on the receiver side. */

        // First decrypt the AES key with RSA public key
        String decryptedAESKeyString = decryptAESKey(encryptedAESKeyString, publicKey);

        // Decrypt data using the decrypted AES key
        String decryptedText = decryptTextUsingAES(encryptedText, decryptedAESKeyString);

        System.out.println("Original message: " + plainText);
        System.out.println("AES key: " + secretAESKeyString);
        System.out.println("Decrypted: " + decryptedText);
    }

    // Create a new AES key. Uses 256 bit (strong)
    public static String getSecretAESKeyAsString() throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        keygen.init(256); // The AES key size in number of bits
        SecretKey secretKey = keygen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    // Encrypt text using AES key
    public static String encryptTextUsingAES(String plainText, String aesKeyString) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(aesKeyString);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        Cipher aesCipher = Cipher.getInstance("AES"); // AES defaults to AES/ECB/PKCS5Padding
        aesCipher.init(Cipher.ENCRYPT_MODE, originalKey);
        byte[] cipherTextBytes = aesCipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(cipherTextBytes);
    }

    // Decrypt text using AES key
    public static String decryptTextUsingAES(String encryptedText, String aesKeyString) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(aesKeyString);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        Cipher aesCipher = Cipher.getInstance("AES"); // AES defaults to AES/ECB/PKCS5Padding
        aesCipher.init(Cipher.DECRYPT_MODE, originalKey);
        byte[] plainTextBytes = aesCipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(plainTextBytes);
    }

    // Decrypt AES key using RSA public key
    private static String decryptAESKey(String encryptedAESKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedAESKey)));
    }

    // Encrypt AES key using RSA private key
    private static String encryptAESKey(String plainAESKey, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainAESKey.getBytes()));
    }
}