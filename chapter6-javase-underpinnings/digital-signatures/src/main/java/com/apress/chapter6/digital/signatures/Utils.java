package com.apress.chapter6.digital.signatures;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

public class Utils {

    private static final String STORE_TYPE = "PKCS12";
    private static final char[] PASSWORD = "changeit".toCharArray();

    private static final String SENDER_KEYSTORE = "senderkeystore.p12";
    private static final String SENDER_ALIAS = "senderKeyPair";
    private static final String RECEIVER_KEYSTORE = "receiverkeystore.p12";
    private static final String RECEIVER_ALIAS = "receiverKeyPair";

    public static final String SIGNING_ALGORITHM = "SHA256withRSA";

    public static PrivateKey getPrivateKey() throws Exception {
        KeyStore keystore = KeyStore.getInstance(STORE_TYPE);
        keystore.load(getResourceAsStream(SENDER_KEYSTORE), PASSWORD);
        return (PrivateKey) keystore.getKey(SENDER_ALIAS, PASSWORD);
    }

    public static PublicKey getPublicKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(STORE_TYPE);
        keyStore.load(getResourceAsStream(RECEIVER_KEYSTORE), PASSWORD);
        Certificate certificate = keyStore.getCertificate(RECEIVER_ALIAS);
        return certificate.getPublicKey();
    }

    public static InputStream getResourceAsStream(String resource) {
        return Utils.class.getClassLoader().getResourceAsStream(resource);
    }

    public static Path getTargetDirPath (String digsig) {
        return Paths.get(Utils.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getFile() +
                digsig);
    }
}
