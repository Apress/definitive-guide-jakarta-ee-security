package com.apress.chapter6.digital.signatures.certgen;

import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.X509ExtensionUtils;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * Generate self-signed certificate by utilizing BC's capabilities
 */
public class GenerateX509v3CertificateUp {

    private static final String CERTIFICATE_PATH = "chapter6-javase-underpinnings/digital-signatures/src/main/resources/";

    public static void main(String[] args) throws Exception {
        // generate asymmetric key pair
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        keygen.initialize(2048);
        KeyPair keyPair = keygen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();

        // set expiry date
        Instant now = Instant.now();
        Date notBeforeDate = Date.from(now);
        Date notAfterDate = Date.from(now.plus(Duration.ofDays(365)));

        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSAEncryption").build(keyPair.getPrivate());
        X500Name x500Name = new X500Name("CN=Test CN");
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                x500Name,
                BigInteger.valueOf(now.toEpochMilli()),
                notBeforeDate,
                notAfterDate,
                x500Name,
                publicKey
        ) // add extensions
                .addExtension(Extension.basicConstraints, true, new BasicConstraints(true)) // basic

                // subjectKeyId
                .addExtension(Extension.subjectKeyIdentifier, false,
                        new X509ExtensionUtils(new BcDigestCalculatorProvider().get(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1)))
                                .createSubjectKeyIdentifier(SubjectPublicKeyInfo.getInstance(publicKey.getEncoded())))

                // authorityKeyId
                .addExtension(Extension.authorityKeyIdentifier, false,
                        new X509ExtensionUtils(new BcDigestCalculatorProvider().get(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1)))
                                .createAuthorityKeyIdentifier(SubjectPublicKeyInfo.getInstance(publicKey.getEncoded())));

        X509Certificate x509Certificate = new JcaX509CertificateConverter().getCertificate(certBuilder.build(contentSigner));
        System.out.println("X509v3 certificate has successfully been generated");

        // validate it
        x509Certificate.checkValidity(new Date(System.currentTimeMillis()));
        x509Certificate.verify(x509Certificate.getPublicKey());
        System.out.println("Certificate is valid, storing in file...");

        // store in file, encoded in binary DER format
        FileOutputStream fos = new FileOutputStream(CERTIFICATE_PATH + "certgenup.der");
        fos.write(x509Certificate.getEncoded());
        fos.close();
        System.out.println("Certificate is now stored in file");
    }
}
