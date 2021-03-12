package com.apress.chapter6.pki.ecc;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public class SimpleECIESExample {

    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        String message = "This is a long message";

        KeyPairGenerator ecKeyGen = KeyPairGenerator.getInstance("EC", "BC");
        ecKeyGen.initialize(new ECGenParameterSpec("brainpoolP384r1"));
        KeyPair ecKeyPair = ecKeyGen.generateKeyPair();

        Cipher iesCipher = Cipher.getInstance("ECIESwithAES-CBC");
        iesCipher.init(Cipher.ENCRYPT_MODE, ecKeyPair.getPublic());

        byte[] ciphertext = iesCipher.doFinal(message.getBytes());

        iesCipher.init(Cipher.DECRYPT_MODE, ecKeyPair.getPrivate(), iesCipher.getParameters());
        byte[] plaintext = iesCipher.doFinal(ciphertext);

        System.out.println(Base64.getEncoder().encodeToString(ciphertext));
        System.out.println(new String(plaintext));
    }
}