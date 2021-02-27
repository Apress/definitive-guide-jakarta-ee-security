package com.apress.chapter6.jce.providers;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class RegisterBCProviderAtSpecificPositionAtRuntime {

    public static void main(String[] args) {

        BouncyCastleProvider provider = new BouncyCastleProvider();
        int pos = Security.insertProviderAt(provider, 1);

        System.out.println("The BouncyCastle Provider is now set to position " + pos);
    }
}