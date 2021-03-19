package com.apress.chapter6.pki.keyagreement;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.KeyAgreement;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.Security;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.util.Base64;

/**
 * Simple example that demonstrates Diffie-Hellman algorithm using Elliptic Curve cryptography.
 */
public class SimpleECDHExample {

    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider())   ;

        // EC setup
        EllipticCurve curve = new EllipticCurve(new ECFieldFp(new BigInteger("fffffffffffffffffffffffffffffffeffffffffffffffff", 16)), // p
                new BigInteger("fffffffffffffffffffffffffffffffefffffffffffffffc", 16), // a
                new BigInteger("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1", 16)); // b

        ECParameterSpec ecSpec = new ECParameterSpec(curve, new ECPoint(new BigInteger("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012", 16),
                new BigInteger("f8e6d46a003725879cefee1294db32298c06885ee186b7ee", 16)), // G
                new BigInteger("ffffffffffffffffffffffff99def836146bc9b1b4d22831", 16), // order
                1); // h

        KeyPairGenerator keygen = KeyPairGenerator.getInstance("ECDH", "BC");
        keygen.initialize(ecSpec);

        // key agreement set up
        KeyAgreement aKeyAgree = KeyAgreement.getInstance("ECDH");
        KeyPair aPair = keygen.generateKeyPair();
        KeyAgreement bKeyAgree = KeyAgreement.getInstance("ECDH");
        KeyPair bPair = keygen.generateKeyPair();

        // two-party agreement
        aKeyAgree.init(aPair.getPrivate());
        bKeyAgree.init(bPair.getPrivate());
        aKeyAgree.doPhase(bPair.getPublic(), true);
        bKeyAgree.doPhase(aPair.getPublic(), true);

        // generate key bytes
        MessageDigest hash = MessageDigest.getInstance("SHA1");
        byte[] aShared = hash.digest(aKeyAgree.generateSecret());
        byte[] bShared = hash.digest(bKeyAgree.generateSecret());

        // verification
        System.out.println(Base64.getEncoder().encodeToString(aShared));
        System.out.println(Base64.getEncoder().encodeToString(bShared));
        System.out.println("The two values "
                + (Base64.getEncoder().encodeToString(aShared)
                .equals(Base64.getEncoder().encodeToString(bShared))
                ? "" : "do not ") + "match");
    }
}
