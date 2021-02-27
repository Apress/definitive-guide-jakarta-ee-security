package com.apress.chapter6.jce.providers.bouncycastle.symmetric.des;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

import java.util.Base64;

public class SimpleCBCEncryptionOnly {

    public static void main(String[] args) throws InvalidCipherTextException {

        String plainText = "This is a secret message";
        String encryptionKey = "56bitkey"; // DES takes a 56-bit key

        BlockCipher desEngine = new DESEngine();
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(desEngine));

        cipher.init(true, new KeyParameter(encryptionKey.getBytes()));
        byte[] cipherText = new byte[cipher.getOutputSize(plainText.getBytes().length)];

        int outputLen = cipher.processBytes(plainText.getBytes(), 0, plainText.getBytes().length, cipherText, 0);
        cipher.doFinal(cipherText, outputLen);

        System.out.println("Plain text: " + plainText);
        System.out.println("Cipher text: " + Base64.getEncoder().encodeToString(cipherText));
    }
}
