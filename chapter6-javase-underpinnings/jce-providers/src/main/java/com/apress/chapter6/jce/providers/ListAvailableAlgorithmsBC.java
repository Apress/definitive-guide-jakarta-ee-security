package com.apress.chapter6.jce.providers;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;

/**
 * This example lists the supported capabilities for Signatures, Ciphers, Key Agreements, MACs, Message Digests,
 *  and other goodies the BC provider ships with.
 */
public class ListAvailableAlgorithmsBC {

    public static void main (String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        Provider provider = Security.getProvider("BC");

        for (Object o : provider.keySet()) {
            String entry = (String) o;

            // alias entries refer to other entries, so let's filter them accordingly
            if (entry.startsWith("Alg.Alias."))
                entry = entry.substring("Alg.Alias.".length());

            String factoryClass = entry.substring(0, entry.indexOf('.'));
            String name = entry.substring(factoryClass.length() + 1);

            System.out.println(factoryClass + ": " + name);
        }
    }
}
