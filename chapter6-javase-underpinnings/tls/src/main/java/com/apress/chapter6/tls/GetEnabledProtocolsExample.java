package com.apress.chapter6.tls;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.util.Arrays;

public class GetEnabledProtocolsExample {

    public static void main(String[] args) throws IOException {

        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = (SSLSocket) factory.createSocket("google.com", 443);
        System.out.println(Arrays.toString(socket.getEnabledProtocols()));
    }
}
