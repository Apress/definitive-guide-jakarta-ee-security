package com.apress.chapter6.tls;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

/**
 * SSLSocket client that uses TLS1.3 protocol and TLS_AES_128_GCM_SHA256 stream cipher.
 * Sends a request to https://google.com and prints the response.
 */
public class SimpleTLS13Example {

    private static final String[] PROTOCOLS = new String[] {"TLSv1.3"};
    private static final String[] CIPHER_SUITES = new String[] {"TLS_AES_128_GCM_SHA256"};

    public static void main(String[] args) throws Exception {

        SSLSocket sslSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // initialize a default socket factory
            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            sslSocket = (SSLSocket) sslSocketFactory.createSocket("google.com", 443);

            sslSocket.setEnabledProtocols(PROTOCOLS); // enable TLS 1.3 protocol
            sslSocket.setEnabledCipherSuites(CIPHER_SUITES); // enable TLS_AES_128_GCM_SHA256 cipher

            sslSocket.startHandshake();

            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sslSocket.getOutputStream())));

            out.println("GET / HTTP/1.0");
            out.println();
            out.flush();

            if (out.checkError())
                System.out.println("SimpleTLS13Example: java.io.PrintWriter error");

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