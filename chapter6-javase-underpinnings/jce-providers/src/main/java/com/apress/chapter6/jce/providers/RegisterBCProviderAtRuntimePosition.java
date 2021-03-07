package com.apress.chapter6.jce.providers;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * Set BouncyCastle provider at specific position at runtime.
 */
public class RegisterBCProviderAtRuntimePosition {

    public static void main(String[] args) {

        BouncyCastleProvider provider = new BouncyCastleProvider();
        String providerName = provider.getName();
        int pos = Security.insertProviderAt(provider, 1);

        if (Security.getProvider(providerName) == null)
            System.out.println(providerName + " provider not installed");
        else
            System.out.println(providerName + " is installed and set at position " + pos);
    }
}