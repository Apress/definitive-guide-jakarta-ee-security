package com.apress.chapter6.jce.providers.bouncycastle.advanced;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * Full cryptography example using Bouncy Castle capabilities.
 * This example demonstrates encryption, decryption, signature creation and verification using Bouncy Castle's light-weight API.
 */
public class BouncyCastleCryptographyExample {

    private static final String PUBLIC_KEY_PATH = "chapter6-javase-underpinnings/digital-signatures/src/main/resources/sender-certificate.cer";
    // the keystore file contains the certificate chain and the private key. we'll use the keystore path to extract the private key
    private static final String KEYSTORE_PATH = "chapter6-javase-underpinnings/digital-signatures/src/main/resources/senderkeystore.p12";
    private static final char[] KEYSTORE_PASSWORD = "changeit".toCharArray();
    private static final char[] PRIVATE_KEY_PASSWORD = "changeit".toCharArray();
    
    public static void main(String[] args) throws Exception{

        Security.addProvider(new BouncyCastleProvider());

        String message = "This is a secret message";
        byte[] messageBytes = message.getBytes();
        System.out.println("Plain: " + Base64.getEncoder().encodeToString(messageBytes));

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", "BC");
        X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(new FileInputStream(PUBLIC_KEY_PATH));
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(new FileInputStream(KEYSTORE_PATH), KEYSTORE_PASSWORD);
        PrivateKey privateKey = (PrivateKey) keystore.getKey("senderKeyPair", PRIVATE_KEY_PASSWORD);

        byte[] cipherText = encrypt(messageBytes, certificate);
        System.out.println("Cipher: " + Base64.getEncoder().encodeToString(cipherText));
        byte[] decryptedMessageBytes = decrypt(cipherText, privateKey);
        System.out.println("Decrypted: " + Base64.getEncoder().encodeToString(decryptedMessageBytes));
        System.out.println("Original and decrypted values " +
                (message.equals(new String(decryptedMessageBytes)) ? "" : "do not ") +
                "match");

        byte[] signatureBytes = sign(decryptedMessageBytes, certificate, privateKey);
        boolean isVerified = verifySignature(signatureBytes);
        System.out.println("\nSignature " + (isVerified ? "verified" : "not verified"));
    }

    public static byte[] encrypt(byte[] dataToEncrypt, X509Certificate encryptionCertificate)
            throws CertificateEncodingException, CMSException, IOException {

        CMSEnvelopedDataGenerator cmsEnvelopedDataGenerator = new CMSEnvelopedDataGenerator();
        JceKeyTransRecipientInfoGenerator jceKey = new JceKeyTransRecipientInfoGenerator(encryptionCertificate);
        cmsEnvelopedDataGenerator.addRecipientInfoGenerator(jceKey);
        CMSTypedData message = new CMSProcessableByteArray(dataToEncrypt);
        OutputEncryptor encryptor = new JceCMSContentEncryptorBuilder(CMSAlgorithm.AES256_CBC).setProvider("BC").build();
        CMSEnvelopedData cmsEnvelopedData = cmsEnvelopedDataGenerator.generate(message, encryptor);

        return cmsEnvelopedData.getEncoded();
    }

    public static byte[] decrypt(byte[] cipherText, PrivateKey privateKey) throws CMSException {

        CMSEnvelopedData cmsEnvelopedData = new CMSEnvelopedData(cipherText);
        Collection<RecipientInformation> recipients = cmsEnvelopedData.getRecipientInfos().getRecipients();
        KeyTransRecipientInformation recipientInfo = (KeyTransRecipientInformation) recipients.iterator().next();
        JceKeyTransRecipient recipient = new JceKeyTransEnvelopedRecipient(privateKey);

        return recipientInfo.getContent(recipient);
    }

    public static byte[] sign(byte[] dataToSign, X509Certificate signingCertificate, PrivateKey privateKey)
            throws CertificateEncodingException, OperatorCreationException, CMSException, IOException {

        List<X509Certificate> certificates = new ArrayList<>();
        CMSTypedData cmsTypedData = new CMSProcessableByteArray(dataToSign);
        certificates.add(signingCertificate);
        Store jcaCertStore = new JcaCertStore(certificates);

        CMSSignedDataGenerator cmsSignedDataGenerator = new CMSSignedDataGenerator();
        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA").build(privateKey);
        cmsSignedDataGenerator.addSignerInfoGenerator(
                new JcaSignerInfoGeneratorBuilder(
                        new JcaDigestCalculatorProviderBuilder().setProvider("BC").build()
                ).build(contentSigner, signingCertificate));
        cmsSignedDataGenerator.addCertificates(jcaCertStore);
        CMSSignedData cmsSignedData = cmsSignedDataGenerator.generate(cmsTypedData, true);

        return cmsSignedData.getEncoded();
    }

    public static boolean verifySignature(byte[] signatureBytes)
            throws CMSException, IOException, OperatorCreationException, CertificateException {

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(signatureBytes);
        ASN1InputStream asn1InputStream = new ASN1InputStream(byteArrayInputStream);
        CMSSignedData cmsSignedData = new CMSSignedData(ContentInfo.getInstance(asn1InputStream.readObject()));

        Store certificates = cmsSignedData.getCertificates();
        SignerInformationStore signerInformationStore = cmsSignedData.getSignerInfos();
        Collection<SignerInformation> signerInformationCollection = signerInformationStore.getSigners();
        SignerInformation signerInformation = signerInformationCollection.iterator().next();
        Collection<X509CertificateHolder> certificateHolders = certificates.getMatches(signerInformation.getSID());
        Iterator<X509CertificateHolder> certificateHolderIterator = certificateHolders.iterator();
        X509CertificateHolder certificateHolder = certificateHolderIterator.next();

        return signerInformation.verify(new JcaSimpleSignerInfoVerifierBuilder().build(certificateHolder));
    }
}