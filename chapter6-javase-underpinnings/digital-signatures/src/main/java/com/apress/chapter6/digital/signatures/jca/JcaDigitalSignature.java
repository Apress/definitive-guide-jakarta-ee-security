package com.apress.chapter6.digital.signatures.jca;

import com.apress.chapter6.digital.signatures.Utils;

import java.nio.file.Files;
import java.security.PrivateKey;
import java.security.Signature;

/**
 * This code example demonstrates the creation of a digital signature
 * using JCA's dedicated API provided by the Signature class.
 */
public class JcaDigitalSignature {

    public static void main(String[] args) throws Exception {

        PrivateKey privateKey = Utils.getPrivateKey();
        Signature signature = Signature.getInstance(Utils.SIGNING_ALGORITHM);
        signature.initSign(privateKey);

        byte[] messageBytes = Utils.getResourceAsStream("message.txt").readAllBytes();
        signature.update(messageBytes);

        byte[] digitalSignature = signature.sign();

        Files.write(Utils.getTargetDirPath("/digital-signature"), digitalSignature);
    }
}
