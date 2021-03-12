package com.apress.chapter6.pki.ecc;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;

/**
 * Key generation example using an Elliptic Curve
 */
public class KeyPairGenNamedCurveExample {

    public static void main(String[] args) throws Exception {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v3");
        kpg.initialize(ecSpec);

        KeyPair keyPair = kpg.genKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        System.out.println(publicKey.toString());
        System.out.println(privateKey.toString());
    }
}
