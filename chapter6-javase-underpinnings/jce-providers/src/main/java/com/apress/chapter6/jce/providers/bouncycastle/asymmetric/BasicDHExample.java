package com.apress.chapter6.jce.providers.bouncycastle.asymmetric;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;
import java.math.BigInteger;
import java.security.*;
import java.util.Base64;

/**
 * Simple example demonstrating a two party key agreement using Diffie-Hellman and Bouncy Castle.
 */
public class BasicDHExample {

    private static final BigInteger g512 = new BigInteger("153d5d6172adb43045b68ae8e1de1070b6137005686d29d3d73a7"
            + "749199681ee5b212c9b96bfdcfa5b20cd5e3fd2044895d609cf9b"
            + "410b7a0f12ca1cb9a428cc", 16);

    private static final BigInteger p512 = new BigInteger("9494fec095f3b85ee286542b3836fc81a5dd0a0349b4c239dd387"
            + "44d488cf8e31db8bcb7d33b41abb9e5a33cca9144b1cef332c94b"
            + "f0573bf047a3aca98cdf3b", 16);

    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        DHParameterSpec dhParams = new DHParameterSpec(p512, g512);
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("DH", "BC");

        keygen.initialize(dhParams, new SecureRandom());

        KeyAgreement aKeyAgree = KeyAgreement.getInstance("DH", "BC");
        KeyPair aPair = keygen.generateKeyPair();
        KeyAgreement bKeyAgree = KeyAgreement.getInstance("DH", "BC");
        KeyPair bPair = keygen.generateKeyPair();

        aKeyAgree.init(aPair.getPrivate());
        bKeyAgree.init(bPair.getPrivate());

        aKeyAgree.doPhase(bPair.getPublic(), true);
        bKeyAgree.doPhase(aPair.getPublic(), true);

        MessageDigest hash = MessageDigest.getInstance("SHA1", "BC");
        byte[] aShared = hash.digest(aKeyAgree.generateSecret());
        byte[] bShared = hash.digest(bKeyAgree.generateSecret());

        System.out.println(Base64.getEncoder().encodeToString(aShared));
        System.out.println(Base64.getEncoder().encodeToString(bShared));
        System.out.println("The two values "
                + (Base64.getEncoder().encodeToString(aShared)
                .equals(Base64.getEncoder().encodeToString(bShared))
                ? "" : "do not ") + "match");
    }
}
