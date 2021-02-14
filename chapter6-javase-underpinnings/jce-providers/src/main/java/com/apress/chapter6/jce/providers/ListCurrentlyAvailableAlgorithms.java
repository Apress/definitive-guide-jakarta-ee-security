package com.apress.chapter6.jce.providers;

import java.security.Provider;
import java.security.Security;
import java.util.*;

/**
 * Prints the currently available algorithms for
 * Ciphers, Key Agreements, MACs, Message Digests and Signatures.
 */
public class ListCurrentlyAvailableAlgorithms {

    public static void main(String[] args) {

        Set<String> ciphers = new TreeSet<>();
        Set<String> keyAgreements = new TreeSet<>();
        Set<String> messageDigests = new TreeSet<>();
        Set<String> macs = new TreeSet<>();
        Set<String> signatures = new TreeSet<>();

        for (Provider p : Security.getProviders()) {

            for (Object o : p.keySet()) {
                String entry = (String) o;

                if (entry.startsWith("Alg.Alias."))
                    entry = entry.substring("Alg.Alias.".length());

                if (entry.startsWith("Cipher."))
                    ciphers.add(entry.substring("Cipher.".length()));

                else if (entry.startsWith("KeyAgreement."))
                    keyAgreements.add(entry.substring("KeyAgreement.".length()));

                else if (entry.startsWith("MessageDigest."))
                    messageDigests.add(entry.substring("MessageDigest.".length()));

                else if (entry.startsWith("Mac."))
                    macs.add(entry.substring("Mac.".length()));

                else if (entry.startsWith("Signature."))
                    signatures.add(entry.substring("Signature.".length()));
            }
        }
        printSet("Ciphers", ciphers);
        printSet("Key Agreements", keyAgreements);
        printSet("Message Digests", messageDigests);
        printSet("MACs", macs);
        printSet("Signatures", signatures);
    }

    public static void printSet(String setName, Set<String> algorithms) {
        System.out.println(setName + ":");

        if (algorithms.isEmpty()) {
            System.out.println("\t No algorithms available for this set.");
        }
        else {
            for (String name : algorithms) {
                System.out.println("\t" + name);
            }
            System.out.println();
        }
    }
}