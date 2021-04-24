package com.apress.chapter6.jce.providers;

import java.security.Provider;
import java.security.Security;
import java.util.*;

/**
 * Prints the currently available algorithms for
 * Signatures, Ciphers, Key Agreements, MACs, and Message Digests.
 */
public class ListAvailableAlgorithms {

    public static void main(String[] args) {

        Set<String> signatures = new TreeSet<>();
        Set<String> ciphers = new TreeSet<>();
        Set<String> keyAgreements = new TreeSet<>();
        Set<String> macs = new TreeSet<>();
        Set<String> messageDigests = new TreeSet<>();

        for (Provider provider : Security.getProviders()) {

            provider.getServices().stream()
                    .filter(s -> "Signature".equals(s.getType()))
                    .map(Provider.Service::getAlgorithm)
                    .forEach(signatures::add);

            provider.getServices().stream()
                    .filter(s -> "Cipher".equals(s.getType()))
                    .map(Provider.Service::getAlgorithm)
                    .forEach(ciphers::add);

            provider.getServices().stream()
                    .filter(s -> "KeyAgreement".equals(s.getType()))
                    .map(Provider.Service::getAlgorithm)
                    .forEach(keyAgreements::add);

            provider.getServices().stream()
                    .filter(s -> "Mac".equals(s.getType()))
                    .map(Provider.Service::getAlgorithm)
                    .forEach(macs::add);

            provider.getServices().stream()
                    .filter(s -> "MessageDigest".equals(s.getType()))
                    .map(Provider.Service::getAlgorithm)
                    .forEach(messageDigests::add);
        }

        System.out.println("=== Signatures ===");
        signatures.forEach(System.out::println);

        System.out.println("\n=== Ciphers ===");
        ciphers.forEach(System.out::println);

        System.out.println("\n=== Key Agreements ===");
        keyAgreements.forEach(System.out::println);

        System.out.println("\n=== Macs ===");
        macs.forEach(System.out::println);

        System.out.println("\n=== Message Digests ===");
        messageDigests.forEach(System.out::println);
    }
}