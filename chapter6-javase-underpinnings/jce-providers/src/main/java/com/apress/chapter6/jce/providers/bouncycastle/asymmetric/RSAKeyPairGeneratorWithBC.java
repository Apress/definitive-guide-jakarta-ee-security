package com.apress.chapter6.jce.providers.bouncycastle.asymmetric;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;

import javax.persistence.Convert;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * A simple example demonstrating an RSA key pair generation utilizing Bouncy Castle.
 */
public class RSAKeyPairGeneratorWithBC {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {

        RSAKeyPairGenerator rsaKeyPairGenerator = new RSAKeyPairGenerator();
        rsaKeyPairGenerator.init(new RSAKeyGenerationParameters
                (
                        new BigInteger("10001", 16), // public exponent
                        SecureRandom.getInstance("SHA1PRNG"), // pseudo random generator
                        1024, // strength
                        80 // certainty
                ));

        AsymmetricCipherKeyPair keyPair = rsaKeyPairGenerator.generateKeyPair();

        byte[] publicKeyDer = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(keyPair.getPublic()).getEncoded();
        String publicKeyDerBase64 = Base64.getEncoder().encodeToString(publicKeyDer);
        System.out.println(publicKeyDerBase64);

        byte[] privateKeyDer = PrivateKeyInfoFactory.createPrivateKeyInfo(keyPair.getPrivate()).getEncoded();
        String privateKeyDerBase64 = Base64.getEncoder().encodeToString(privateKeyDer);
        System.out.println(privateKeyDerBase64);
    }
}
