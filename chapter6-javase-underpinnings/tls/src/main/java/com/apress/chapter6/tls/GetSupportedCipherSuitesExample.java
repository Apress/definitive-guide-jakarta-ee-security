package com.apress.chapter6.tls;

import javax.net.ssl.SSLSocketFactory;
import java.util.Arrays;

public class GetSupportedCipherSuitesExample {
    public static void main(String[] args) {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        System.out.println(Arrays.toString(factory.getSupportedCipherSuites()));
    }
}
