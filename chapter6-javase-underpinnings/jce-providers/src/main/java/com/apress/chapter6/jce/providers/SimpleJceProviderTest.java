package com.apress.chapter6.jce.providers;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * This example demonstrates how to confirm whether the BouncyCastle provider is installed.
 */
public class SimpleJceProviderTest {

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        String providerName = "BC";

        if (Security.getProvider(providerName) == null)
            System.out.println(providerName + " provider not installed");
        else
            System.out.println(providerName + " is installed.");
    }
}