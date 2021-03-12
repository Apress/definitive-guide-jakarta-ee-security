package com.apress.chapter6.pki.ecc;

import java.security.*;

/**
 * A simple example that demonstrates DSA algorithm.
 * WARNING: DSA stands for Digital Signature Algorithm and is designed to produce digital signatures, not to perform encryption.
 *
 * We shouldn't confuse the need for key pairs among RSA and DSA.
 * For the former, a key pair is needed so anyone can encrypt, whereas for the latter, a key is needed so anyone can verify.
 * Another difference is that RSA uses a private key for decryption, whereas DSA utilizes it to create a signature.
 */
public class SignatureDSAExample {

    public static void main(String[] args) throws Exception {

        byte[] messageBytes = "This is a secret message".getBytes();

        // generate key pair
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
        keygen.initialize(512, new SecureRandom());
        KeyPair keyPair = keygen.generateKeyPair();

        // generate a signature
        Signature signature = Signature.getInstance("DSA");
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
