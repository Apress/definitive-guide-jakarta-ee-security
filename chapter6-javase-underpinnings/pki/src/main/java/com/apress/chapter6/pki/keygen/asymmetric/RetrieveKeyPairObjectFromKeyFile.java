package com.apress.chapter6.pki.keygen.asymmetric;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * This example demonstrates how to retrieve asymmetric key pair objects from asymmetric key files.
 * here we use the key pair generated in digital signatures chapter/module.
 *
 * Public keys are encoded in X.509 format, therefore the X509EncodedKeySpec transformation
 * Private keys are encoded in PKCS#8 format, therefore the PKCS8EncodedKeySpec transformation
 */
public class RetrieveKeyPairObjectFromKeyFile {

    private static final String PUBLIC_KEY_PATH = "chapter6-javase-underpinnings/jce-providers/src/main/resources/rsa-keys/id_rsa.pub";
    private static final String PRIVATE_KEY_PATH = "chapter6-javase-underpinnings/jce-providers/src/main/resources/rsa-keys/id_rsa.key";

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

        Path publicKeyPath = Paths.get(PUBLIC_KEY_PATH);
        Path privateKeyPath = Paths.get(PRIVATE_KEY_PATH);

        byte[] publicKeyBytes = Files.readAllBytes(publicKeyPath);
        byte[] privateKeyBytes = Files.readAllBytes(privateKeyPath);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        System.out.println("Private Key: \n" + privateKey.toString());
        System.out.println("Public Key: \n" + publicKey.toString());
    }
}
