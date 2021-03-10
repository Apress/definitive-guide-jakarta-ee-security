package com.apress.chapter6.jce.providers.bouncycastle.asymmetric;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * A simple example demonstrating an RSA key pair generation utilizing Bouncy Castle's light-weight API.
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
        System.out.println("Public key: " + publicKeyDerBase64);

        byte[] privateKeyDer = PrivateKeyInfoFactory.createPrivateKeyInfo(keyPair.getPrivate()).getEncoded();
        String privateKeyDerBase64 = Base64.getEncoder().encodeToString(privateKeyDer);
        System.out.println("Private key: " + privateKeyDerBase64);
    }

    /**
     * This method demonstrates how to convert a BC key pair back to JCE.
     * It is not used in this code example, but it's here to notify you on this possibility as well.
     */
    private static KeyPair convertBCKeyPairToJCEKeyPair(AsymmetricCipherKeyPair bcKeyPair)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] pkcs8Encoded = PrivateKeyInfoFactory.createPrivateKeyInfo(bcKeyPair.getPrivate()).getEncoded();
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pkcs8Encoded);
        byte[] spkiEncoded = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(bcKeyPair.getPublic()).getEncoded();
        X509EncodedKeySpec spkiKeySpec = new X509EncodedKeySpec(spkiEncoded);
        KeyFactory jceKeyFactory = KeyFactory.getInstance("RSA");

        return new KeyPair(jceKeyFactory.generatePublic(spkiKeySpec), jceKeyFactory.generatePrivate(pkcs8KeySpec));
    }
}
