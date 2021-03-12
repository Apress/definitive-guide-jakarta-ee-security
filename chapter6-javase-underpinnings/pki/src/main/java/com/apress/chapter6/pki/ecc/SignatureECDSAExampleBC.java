package com.apress.chapter6.pki.ecc;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;

import java.security.*;

/**
 * Simple example that demonstrates signature creation and verification using ECDSA.
 * Here, all supportive classes for curve configuration come from BC
 */
public class SignatureECDSAExampleBC {

    public static void main(String[] args) throws Exception {

        Security.addProvider(new BouncyCastleProvider());
        byte[] messageBytes = "This is a secret message".getBytes();

        // generate key pair
        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("B-571"); // here we're using BC's lightweight API
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("ECDSA", "BC");
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
