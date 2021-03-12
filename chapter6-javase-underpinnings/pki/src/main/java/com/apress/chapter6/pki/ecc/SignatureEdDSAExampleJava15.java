package com.apress.chapter6.pki.ecc;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.util.Base64;

/**
 * Java 15 signature example on EdDSA algorithm
 */
public class SignatureEdDSAExampleJava15 {

    public static void main(String[] args) throws Exception{

        byte[] messageBytes = "This is a secret message".getBytes();

        KeyPairGenerator keygen = KeyPairGenerator.getInstance("Ed25519");
        KeyPair keyPair = keygen.generateKeyPair();

        Signature signature = Signature.getInstance("Ed25519");
        signature.initSign(keyPair.getPrivate());
        signature.update(messageBytes);
        byte[] signatureBytes = signature.sign();
        System.out.println("Encoded string: " + Base64.getEncoder().encodeToString(signatureBytes));

        // verify signature
        signature.initVerify(keyPair.getPublic());
        signature.update(messageBytes);
        boolean isVerified = signature.verify(signatureBytes);

        System.out.println("Signature " + (isVerified ? "verified" : "not verified"));
    }
}
