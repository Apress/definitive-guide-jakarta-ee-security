package com.apress.chapter6.jce.providers.bundled.asymmetric;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

/**
 * A simple example that generates an asymmetric key pair.
 * The generated keys are saved in the resources folder.
 */
public class RSAKeyPairGenerator {

    private static final String PATH = "chapter6-javase-underpinnings/jce-providers/src/main/resources/rsa-keys/";

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public RSAKeyPairGenerator() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public void saveKeyInFile(byte[] key, String path) throws IOException {
        File f = new File(PATH + path);
        f.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        RSAKeyPairGenerator keyPairGenerator = new RSAKeyPairGenerator();
        keyPairGenerator.saveKeyInFile(keyPairGenerator.getPublicKey().getEncoded(),"id_rsa.pub");
        keyPairGenerator.saveKeyInFile(keyPairGenerator.getPrivateKey().getEncoded(), "id_rsa.key");

        System.out.println("Public key: " + Base64.getEncoder().encodeToString(keyPairGenerator.getPublicKey().getEncoded()));
        System.out.println("Private key: " + Base64.getEncoder().encodeToString(keyPairGenerator.getPrivateKey().getEncoded()));
    }
}