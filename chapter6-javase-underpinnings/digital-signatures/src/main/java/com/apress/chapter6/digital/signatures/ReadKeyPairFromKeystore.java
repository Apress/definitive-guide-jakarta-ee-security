package com.apress.chapter6.digital.signatures;

public class ReadKeyPairFromKeystore {

    public static void main(String[] args) throws Exception {

        System.out.println(Utils.getPrivateKey());
        System.out.println(Utils.getPublicKey());
    }
}
