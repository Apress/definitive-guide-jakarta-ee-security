package com.apress.chapter6.jce.providers.bouncycastle.symmetric.aes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.KCTRBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES encryption example in CTR mode with NoPadding.
 *
 * Key features of this example:
 * 1. Uses BouncyCastle's light-weight API to demonstrate a cipher creation
 * 2. Uses a temporary buffer for encryption and decryption
 * 3. Uses BouncyCastle's KeyParameter class to generate a key
 */
public class SimpleCTRExampleWithBC {

    private static final int AES_KEY_SIZE = 256;

    public static void main(String[] args) throws InvalidCipherTextException {

        byte[] messageBytes = "This is a secret message".getBytes();

        // this block is equivalent to Cipher.getInstance("AES/CTR/NoPadding", new BouncyCastleProvider());
        BlockCipher aes = new AESEngine();
        KCTRBlockCipher aesCBC = new KCTRBlockCipher(aes);
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(aesCBC, new ZeroBytePadding());

        // create IV
        byte[] iv = new byte[aes.getBlockSize()];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        // initialize cipher with IV
        ParametersWithIV paramsWithIV = new ParametersWithIV(generateKey(), iv);
        // PaddedBufferedBlockCipher#init has a different signature than JCA's Cipher#init
        cipher.init(true, paramsWithIV);

        // encrypt
        byte[] cipherText = new byte[cipher.getOutputSize(messageBytes.length)];
        int bytesWrittenOut = cipher.processBytes(messageBytes, 0, messageBytes.length, cipherText, 0);
        cipher.doFinal(cipherText, bytesWrittenOut);

        System.out.println("Original: " + Base64.getEncoder().encodeToString(messageBytes));
        System.out.println("Cipher: " + Base64.getEncoder().encodeToString(cipherText));

        // decrypt
        cipher.init(false, paramsWithIV);

        // create a temporary buffer to decode into - it will include padding
        byte[] buf = new byte[cipher.getOutputSize(cipherText.length)];
        int len = cipher.processBytes(cipherText, 0, cipherText.length, buf, 0);
        len += cipher.doFinal(buf, len);

        // remove padding
        byte[] out = new byte[len];
        System.arraycopy(buf, 0, out, 0, len);

        System.out.println("Decrypted: " + Base64.getEncoder().encodeToString(out));
        System.out.println("Original and decrypted values " +
                (new String(messageBytes).equals(new String(out)) ? "" : "do not ") +
                "match");
    }

    private static KeyParameter generateKey() {
        SecureRandom keyRNG = new SecureRandom();
        byte[] keyData = new byte[AES_KEY_SIZE / Byte.SIZE];
        keyRNG.nextBytes(keyData);
        return new KeyParameter(keyData);
    }
}
