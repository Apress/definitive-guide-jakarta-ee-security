package com.apress.chapter6.pki.keyagreement;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;
import java.math.BigInteger;
import java.security.*;
import java.util.Base64;

/**
 * Simple example demonstrating a three-way key agreement using Diffie-Hellman.
 */
public class ThreeWayDHExample {

    private static final BigInteger g512 = new BigInteger("153d5d6172adb43045b68ae8e1de1070b6137005686d29d3d73a7"
            + "749199681ee5b212c9b96bfdcfa5b20cd5e3fd2044895d609cf9b"
            + "410b7a0f12ca1cb9a428cc", 16);

    private static final BigInteger p512 = new BigInteger("9494fec095f3b85ee286542b3836fc81a5dd0a0349b4c239dd387"
            + "44d488cf8e31db8bcb7d33b41abb9e5a33cca9144b1cef332c94b"
            + "f0573bf047a3aca98cdf3b", 16);

    public static void main(String[] args) throws Exception {

        DHParameterSpec dhParams = new DHParameterSpec(p512, g512);
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("DH");
        keygen.initialize(dhParams, new SecureRandom());

        KeyAgreement aKeyAgreement = KeyAgreement.getInstance("DH");
        KeyPair aPair = keygen.generateKeyPair();
        KeyAgreement bKeyAgreement = KeyAgreement.getInstance("DH");
        KeyPair bPair = keygen.generateKeyPair();
        KeyAgreement cKeyAgreement = KeyAgreement.getInstance("DH");
        KeyPair cPair = keygen.generateKeyPair();

        aKeyAgreement.init(aPair.getPrivate());
        bKeyAgreement.init(bPair.getPrivate());
        cKeyAgreement.init(cPair.getPrivate());

        Key ac = aKeyAgreement.doPhase(cPair.getPublic(), false);
        Key ba = bKeyAgreement.doPhase(aPair.getPublic(), false);
        Key cb = cKeyAgreement.doPhase(bPair.getPublic(), false);

        aKeyAgreement.doPhase(cb, true);
        bKeyAgreement.doPhase(ac, true);
        cKeyAgreement.doPhase(ba, true);

        MessageDigest hash = MessageDigest.getInstance("SHA1");
        byte[] aShared = hash.digest(aKeyAgreement.generateSecret());
        byte[] bShared = hash.digest(bKeyAgreement.generateSecret());
        byte[] cShared = hash.digest(cKeyAgreement.generateSecret());

        String a = Base64.getEncoder().encodeToString(aShared);
        String b = Base64.getEncoder().encodeToString(bShared);
        String c = Base64.getEncoder().encodeToString(cShared);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println("The three values "
                + (a.equals(b) && b.equals(c) && a.equals(c)
                ? "" : "do not ") + "match");
    }
}