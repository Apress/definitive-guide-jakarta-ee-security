package com.apress.chapter6.pki.mac;

import java.security.Provider;
import java.security.Security;
import java.util.Set;
import java.util.TreeSet;

public class ListAvailableHashFunctions {

    public static void main(String[] args) {

        Set<String> messageDigests = new TreeSet<>();
        for (Provider provider : Security.getProviders()) {
            provider.getServices().stream()
                    .filter(s -> "MessageDigest".equals(s.getType()))
                    .map(Provider.Service::getAlgorithm)
                    .forEach(messageDigests::add);
        }
        messageDigests.forEach(System.out::println);
    }
}
