package com.apress.chapter6.jce.providers.bundled.asymmetric;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * A simple example to generate and verify a digital signature using RSA.
 */
public class RSAUtils {

    private static final String PATH = "chapter6-javase-underpinnings/jce-providers/src/main/resources/rsa-keys/";

    public static void main(String[] args)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {

        PublicKey publicKey = RSAUtils.getPublicKey("id_rsa.pub");
        PrivateKey privateKey = RSAUtils.getPrivateKey("id_rsa.key");

        System.out.println(publicKey);
        System.out.println(privateKey);

        byte[] messageBytes = "This is a test message".getBytes();
        byte[] digitalSignature = RSAUtils.sign(messageBytes, privateKey);
        System.out.println("\nDigital signature: " + Base64.getEncoder().encodeToString(digitalSignature));

        boolean isVerified = RSAUtils.verifySignature(messageBytes, publicKey, digitalSignature);
        System.out.println("Signature " + (isVerified ? "verified" : "not verified"));
    }

    public static PublicKey getPublicKey (String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Path path = Paths.get(PATH + filename);
        byte[] bytes = Files.readAllBytes(path);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey getPrivateKey (String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Path path = Paths.get(PATH + filename);
        byte[] bytes = Files.readAllBytes(path);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static byte[] sign(byte[] messageBytes, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature= Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(messageBytes);
        return signature.sign();
    }

    public static boolean verifySignature(byte[] messageBytes, PublicKey publicKey, byte[] digitalSignature)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(messageBytes);

        return signature.verify(digitalSignature);
    }
}
