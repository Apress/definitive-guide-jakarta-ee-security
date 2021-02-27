package com.apress.chapter6.jce.providers.bouncycastle.symmetric.des;

import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;
import java.util.Base64;

public class SimpleDESedeEncryptionWithZeroPadding {

    public static void main(String[] args) throws InvalidCipherTextException {

        String plainText = "This is a secret message";
        byte[] plainTextBytes = plainText.getBytes();
        String keyString = "thisisa128bitkey";

        // setup DESede cipher in CBC mode with zero byte padding
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new DESedeEngine()), new ZeroBytePadding());
        cipher.init(true, new KeyParameter(keyString.getBytes()));

        // create a temporary buffer to decode into - it will include padding
        byte[] buf = new byte[cipher.getOutputSize(plainTextBytes.length)];
        int len = cipher.processBytes(plainTextBytes, 0, plainTextBytes.length, buf, 0);
        len += cipher.doFinal(buf, len);
        // remove padding
        byte[] out = new byte[len];
        System.arraycopy(buf, 0, out, 0, len);

        System.out.println("Plain text: " + plainText);
        System.out.println("Cipher text: " + Base64.getEncoder().encodeToString(out));
    }
}
