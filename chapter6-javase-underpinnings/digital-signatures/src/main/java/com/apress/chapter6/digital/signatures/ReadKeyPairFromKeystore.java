package com.apress.chapter6.digital.signatures;

public class ReadKeyPairFromKeystore {

    public static void main(String[] args) throws Exception {
        System.out.println(KeystoreUtils.getPrivateKey());
        System.out.println(KeystoreUtils.getPublicKey());
    }
}
