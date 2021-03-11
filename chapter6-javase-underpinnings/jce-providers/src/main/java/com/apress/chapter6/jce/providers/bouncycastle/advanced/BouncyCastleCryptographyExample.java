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
 * Full cryptography example using Bouncy Castle's light-weight API.
 * This example demonstrates encryption, decryption, signature creation and verification.
 *
 * Steps for encryption
 * 1. Load private and public key from file
 * 2. Sender encrypts a message using the recipient's certificate (public key)
 * 3. Recipient decrypts the message using the associate private key
 *
 * Steps for signature
 * 1. Create a digital signature using a signing certificate and a private key
 * 2. Verify the signature's bytes by cross-checking the certificate holders with the help of ASN.1 standard
 *
 * Other notes
 * 1. This example uses the key pair generated in digital signatures chapter/module
 * 2. sender-certificate.cer is a digital certificate that uses the X.509 standard
 * 3. senderkeystore.p12 is a password-protected PKCS12 keystore that contains a certificate chain and a private key
 * 4. We'll use the keystore path to extract the private key
 */
public class BouncyCastleCryptographyExample {

    private static final String PUBLIC_KEY_PATH = "chapter6-javase-underpinnings/digital-signatures/src/main/resources/sender-certificate.cer";
    private static final String KEYSTORE_PATH = "chapter6-javase-underpinnings/digital-signatures/src/main/resources/senderkeystore.p12";
    private static final char[] KEYSTORE_PASSWORD = "changeit".toCharArray();
    private static final char[] PRIVATE_KEY_PASSWORD = "changeit".toCharArray();
    // a PKCS12 keystore contains a set of private keys and each private key can have a specific password
    // therefore we need a global password to access the keystore, and a specific one to retrieve the private key.
    
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

        // retrieve the intended recipients of the message
        Collection<RecipientInformation> recipients = cmsEnvelopedData.getRecipientInfos().getRecipients();
        KeyTransRecipientInformation recipientInfo = (KeyTransRecipientInformation) recipients.iterator().next();
        // recipientInfo now contains the decrypted message, but we need the corresponding recipient's key to retrieve it

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
                ).build(contentSigner, signingCertificate)); // use contentSigner instance along with the signing certificate
                                                             // to create a SignerInfoGenerator object.

        cmsSignedDataGenerator.addCertificates(jcaCertStore);
        // At this point, the cmsSignedDataGenerator instance contains a SignerInfoGenerator and the signing certificate

        // create a CMS signed-data object, which also carries a CMS signature.
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
        // here we verified only one signer, but it is recommended to iterate over the entire collection of signers
        // returned by the getSigners() method and check each one separately.

        // create a SignerInformationVerifier object (using its build() method) and pass it to the verify() method for verification
        return signerInformation.verify(new JcaSimpleSignerInfoVerifierBuilder().build(certificateHolder));
    }
}