package com.apress.chapter6.pki.mac;

import java.security.Provider;
import java.security.Security;
import java.util.Set;
import java.util.TreeSet;

public class ListAvailableMACs {

    public static void main(String[] args) {

        Set<String> algs = new TreeSet<>();
        for (Provider provider : Security.getProviders()) {
            provider.getServices().stream()
                    .filter(s -> "Mac".equals(s.getType()))
                    .map(Provider.Service::getAlgorithm)
                    .forEach(algs::add);
        }
        algs.forEach(System.out::println);
    }
}
