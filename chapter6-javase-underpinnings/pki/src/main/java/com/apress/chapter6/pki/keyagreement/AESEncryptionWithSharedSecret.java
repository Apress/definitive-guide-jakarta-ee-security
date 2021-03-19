package com.apress.chapter6.pki.keyagreement;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

/**
 * AES encryption using AES to generate a shared secret during the key agreement phase.
 *
 * Steps:
 * 1. Two parties generate their key pairs
 * 2. Two parties generate two AES secret keys
 * 3. Party A encrypts using AES/GCM/NoPadding and its own secret
 * 4. Party B decrypts using AES/GCM/NoPadding and its own secret
 */
public class AESEncryptionWithSharedSecret {

    public static byte[] iv = new SecureRandom().generateSeed(16);

    public static void main(String[] args) throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        String plainText = "This is a secret message";
        System.out.println("Original message: " + plainText);

        // initialize two key pairs
        KeyPair aPair = generateECKeys();
        KeyPair bPair = generateECKeys();

        // create two AES secret keys to encrypt/decrypt the message
        SecretKey aSecret = generateSharedSecret(aPair.getPrivate(), bPair.getPublic());
        SecretKey bSecret = generateSharedSecret(bPair.getPrivate(), aPair.getPublic());

        // encrypt the message using 'aSecret'
        String ciphertext = encryptString(plainText, aSecret);
        System.out.println("Encrypted ciphertext: " + ciphertext);

        // decrypt the message using 'bSecret'
        String decryptedPlainText = decryptString(ciphertext, bSecret);
        System.out.println("Decrypted ciphertext: " + decryptedPlainText);
    }

    public static KeyPair generateECKeys() throws Exception {
        ECNamedCurveParameterSpec ecParams = ECNamedCurveTable.getParameterSpec("secp256r1");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDH", "BC");
        keyPairGenerator.initialize(ecParams);

        return keyPairGenerator.generateKeyPair();
    }

    public static SecretKey generateSharedSecret(PrivateKey privateKey, PublicKey publicKey) throws Exception {
        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", "BC");
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey, true);

        return keyAgreement.generateSecret("AES");
    }

    public static String encryptString(String plainText, SecretKey key) throws Exception {

        byte[] plainTextBytes = plainText.getBytes();
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] ciphertext = new byte[cipher.getOutputSize(plainTextBytes.length)];
        int encryptLength = cipher.update(plainTextBytes, 0, plainTextBytes.length, ciphertext, 0);
        encryptLength += cipher.doFinal(ciphertext, encryptLength);

        return bytesToHex(ciphertext);
    }

    public static String decryptString(String ciphertext, SecretKey key) throws Exception {

        byte[] cipherTextBytes = hexToBytes(ciphertext);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Key decryptionKey = new SecretKeySpec(key.getEncoded(), key.getAlgorithm());

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, decryptionKey, ivSpec);
        byte[] plainText = new byte[cipher.getOutputSize(cipherTextBytes.length)];
        int decryptLength = cipher.update(cipherTextBytes, 0, cipherTextBytes.length, plainText, 0);
        decryptLength += cipher.doFinal(plainText, decryptLength);

        return new String(plainText);
    }

    public static String bytesToHex(byte[] data, int length) {
        String digits = "0123456789ABCDEF";
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i != length; i++) {
            int v = data[i] & 0xff;
            builder.append(digits.charAt(v >> 4));
            builder.append(digits.charAt(v & 0xf));
        }

        return builder.toString();
    }

    public static String bytesToHex(byte[] data) {
        return bytesToHex(data, data.length);
    }

    public static byte[] hexToBytes(String string) {
        int length = string.length();
        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4) + Character
                    .digit(string.charAt(i + 1), 16));
        }
        return data;
    }
}