package com.apress.chapter6.pki.ecc;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

/**
 * Simple example that demonstrates signature creation and verification using ECDSA.
 * ECDSA is only provided by BC, the rest of supportive classes (such as ECGenParameterSpec) come with JCE.
 */
public class SignatureECDSAExample {

    public static void main(String[] args) throws Exception {

        Security.addProvider(new BouncyCastleProvider());
        byte[] messageBytes = "This is a secret message".getBytes();

        // generate key pair
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("ECDSA");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp128r1"); // here we're using JCE's crypto API
        keygen.initialize(ecSpec, new SecureRandom());
        KeyPair keyPair = keygen.generateKeyPair();

        // generate a signature
        Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
        signature.initSign(keyPair.getPrivate());
        signature.update(messageBytes);
        byte[] signatureBytes = signature.sign();

        // verify signature
        signature.initVerify(keyPair.getPublic());
        signature.update(messageBytes);
        boolean isVerified = signature.verify(signatureBytes);

        System.out.println("Signature " + (isVerified ? "verified" : "not verified"));
    }
}
