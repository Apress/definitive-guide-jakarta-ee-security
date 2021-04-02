package com.apress.chapter6.jce.providers;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;

/**
 * This example lists the supported capabilities for
 * Ciphers, Key Agreements, MACs, Message Digests and Signatures,
 * and other goodies the BC provider ships with.
 */
public class ListBCSupportedCapabilities {

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        Provider provider = Security.getProvider("BC");

        for (Object o : provider.keySet()) {
            String entry = (String) o;

            // reference to another entry
            if (entry.startsWith("Alg.Alias."))
                entry = entry.substring("Alg.Alias.".length());

            String factoryClass = entry.substring(0, entry.indexOf('.'));
            String name = entry.substring(factoryClass.length() + 1);

            System.out.println(factoryClass + ": " + name);
        }
    }
}
