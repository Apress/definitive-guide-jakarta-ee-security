package com.apress.chapter6.jce.providers;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.Provider;
import java.security.Security;

/**
 * This example demonstrates how to set BouncyCastle as the default provider.
 */
public class JceProviderPrecedenceTestForceBC {

    public static void main(String[] args) throws Exception {

        Security.insertProviderAt(new BouncyCastleProvider(),1);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        System.out.println(cipher.getProvider());

        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");

        System.out.println(cipher.getProvider());

    }
}
