package com.apress.chapter6.digital.signatures.jca;

import com.apress.chapter6.digital.signatures.Utils;

import java.security.PublicKey;
import java.security.Signature;

public class JcaDigitalSignatureVerification {

    public static void main(String[] args) throws Exception {

        PublicKey publicKey = Utils.getPublicKey();
        Signature signature = Signature.getInstance(Utils.SIGNING_ALGORITHM);
        signature.initVerify(publicKey);

        byte[] messageBytes = Utils.getResourceAsStream("message.txt").readAllBytes();
        signature.update(messageBytes);

        byte[] jcaDigitalSignature = Utils.getResourceAsStream("digital-signature").readAllBytes();
        boolean isVerified = signature.verify(jcaDigitalSignature);
        System.out.println("Signature " + (isVerified ? "verified" : "not verified"));
    }
}
