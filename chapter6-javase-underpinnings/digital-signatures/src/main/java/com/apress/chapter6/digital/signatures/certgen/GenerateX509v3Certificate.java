package com.apress.chapter6.digital.signatures.certgen;

import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import javax.security.auth.x500.X500Principal;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class GenerateX509v3Certificate {

    private static final String CERTIFICATE_PATH = "chapter6-javase-underpinnings/digital-signatures/src/main/resources/";

    public static void main(String[] args) throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        // generate asymmetric key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // set expiry date
        Instant now = Instant.now();
        Date notBefore = Date.from(now);
        Date notAfter = Date.from(now.plus(Duration.ofDays(365)));

        // instantiate a new cert gen
        X509V3CertificateGenerator certgen = new X509V3CertificateGenerator();

        // set attributes
        certgen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        certgen.setIssuerDN(new X500Principal("CN=Test Issuer DN"));
        certgen.setNotBefore(notBefore);
        certgen.setNotAfter(notAfter);
        certgen.setSubjectDN(new X500Principal("CN=Test Subject DN"));
        certgen.setPublicKey(keyPair.getPublic());
        certgen.setSignatureAlgorithm("SHA256WithRSAEncryption");

        // add extensions
        certgen.addExtension(X509Extensions.BasicConstraints, true, new BasicConstraints(false));
        certgen.addExtension(X509Extensions.SubjectAlternativeName, false,
                new GeneralNames(new GeneralName(GeneralName.rfc822Name, "test@test.com")));
        certgen.addExtension(X509Extensions.KeyUsage, true, new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
        certgen.addExtension(X509Extensions.ExtendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));

        // generate the X509v3 cert
        X509Certificate x509Certificate = certgen.generateX509Certificate(keyPair.getPrivate(), "BC");
        System.out.println("X509v3 certificate has successfully been generated");

        // validate it
        x509Certificate.checkValidity(new Date(System.currentTimeMillis()));
        x509Certificate.verify(x509Certificate.getPublicKey());
        System.out.println("Certificate is valid, storing in file...");

        // store in file, encoded in binary DER format
        FileOutputStream fos = new FileOutputStream(CERTIFICATE_PATH + "certgen.der");
        fos.write(x509Certificate.getEncoded());
        fos.close();
        System.out.println("Certificate is now stored in file");
    }
}