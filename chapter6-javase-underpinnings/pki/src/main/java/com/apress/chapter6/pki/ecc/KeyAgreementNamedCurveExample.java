package com.apress.chapter6.pki.ecc;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.KeyAgreement;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

/**
 * Key agreement example using an Elliptic Curve that comes with the bundled JCE provider.
 */
public class KeyAgreementNamedCurveExample {

    public static void main(String[] args) throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        KeyPairGenerator keygen = KeyPairGenerator.getInstance("ECDH", "BC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("sect409r1");
        keygen.initialize(ecSpec);

        KeyAgreement aKeyAgreement = KeyAgreement.getInstance("ECDH", "BC");
        KeyPair aPair = keygen.generateKeyPair();
        KeyAgreement bKeyAgree = KeyAgreement.getInstance("ECDH", "BC");
        KeyPair bPair = keygen.generateKeyPair();

        aKeyAgreement.init(aPair.getPrivate());
        bKeyAgree.init(bPair.getPrivate());

        aKeyAgreement.doPhase(bPair.getPublic(), true);
        bKeyAgree.doPhase(aPair.getPublic(), true);

        MessageDigest hash = MessageDigest.getInstance("SHA1", "BC");
        byte[] aShared = hash.digest(aKeyAgreement.generateSecret());
        byte[] bShared = hash.digest(bKeyAgree.generateSecret());

        System.out.println(Base64.getEncoder().encodeToString(aShared));
        System.out.println(Base64.getEncoder().encodeToString(bShared));
        System.out.println("The two values "
                + (Base64.getEncoder().encodeToString(aShared)
                .equals(Base64.getEncoder().encodeToString(bShared))
                ? "" : "do not ") + "match");
    }
}
