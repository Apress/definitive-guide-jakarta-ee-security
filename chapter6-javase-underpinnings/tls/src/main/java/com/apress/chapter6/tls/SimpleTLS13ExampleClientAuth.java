package com.apress.chapter6.tls;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.security.KeyStore;

/**
 * SSLSocket client that uses TLS1.3 protocol and TLS_AES_128_GCM_SHA256 stream cipher.
 * Sends a request to https://google.com and prints the response.
 * This example demonstrates a client-side authentication by utilizing a client's keystore
 */
public class SimpleTLS13ExampleClientAuth {

    private static final String KEYSTORE_PATH = "chapter6-javase-underpinnings/digital-signatures/src/main/resources/senderkeystore.p12";
    private static final char[] KEYSTORE_PASSWORD = "changeit".toCharArray();
    private static final String[] PROTOCOLS = new String[] {"TLSv1.3"};
    private static final String[] CIPHER_SUITES = new String[] {"TLS_AES_128_GCM_SHA256"};

    public static void main(String[] args) throws Exception {

        SSLSocket sslSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(new FileInputStream(KEYSTORE_PATH), KEYSTORE_PASSWORD);
            keyManagerFactory.init(keystore, KEYSTORE_PASSWORD);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            sslSocket = (SSLSocket) sslSocketFactory.createSocket("google.com", 443);
            sslSocket.setEnabledProtocols(PROTOCOLS); // enable TLS 1.3 protocol
            sslSocket.setEnabledCipherSuites(CIPHER_SUITES); // enable TLS_AES_128_GCM_SHA256 cipher

            sslSocket.startHandshake();

            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sslSocket.getOutputStream())));

            out.println("GET / HTTP/1.0");
            out.println();
            out.flush();

            if (out.checkError())
                System.out.println("SimpleTLS13ExampleClientAuth: java.io.PrintWriter error");

            // read response
            in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sslSocket != null) sslSocket.close();
            if (out != null) out.close();
            if (in != null) in.close();
        }
    }
}