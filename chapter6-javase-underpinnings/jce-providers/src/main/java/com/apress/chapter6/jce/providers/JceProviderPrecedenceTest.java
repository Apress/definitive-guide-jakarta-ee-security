package com.apress.chapter6.jce.providers;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.Security;

/**
 * This example demonstrates the default precedence among bundled and external providers.
 */
public class JceProviderPrecedenceTest {

    public static void main(String[] args) throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        System.out.println(cipher.getProvider());

        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
        System.out.println(cipher.getProvider());

    }
}
