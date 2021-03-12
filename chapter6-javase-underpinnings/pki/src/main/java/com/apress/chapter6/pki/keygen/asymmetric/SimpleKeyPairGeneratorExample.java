package com.apress.chapter6.pki.keygen.asymmetric;

import java.security.*;

/**
 * Generate an asymmetric key pair and retrieve its public and private key.
 */
public class SimpleKeyPairGeneratorExample {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        System.out.println("Public key: " + publicKey);
        System.out.println("Private key: " + privateKey);
    }
}
