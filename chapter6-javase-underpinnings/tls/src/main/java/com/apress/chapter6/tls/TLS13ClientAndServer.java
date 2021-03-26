package com.apress.chapter6.tls;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

/**
 * TLS 1.3 example demonstrating messaging between a client and a server
 * The necessary system properties (keystore and truststore) are loaded using a static block
 * and originate from the official JSSE's regression test suite.
 * System properties are harcoded for demonstration purposes.
 */
public class TLS13ClientAndServer {

    static {
        System.setProperty("javax.net.ssl.keyStore", "chapter6-javase-underpinnings/tls/src/main/resources/keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "passphrase");
        System.setProperty("javax.net.ssl.trustStore", "chapter6-javase-underpinnings/tls/src/main/resources/truststore");
        System.setProperty("javax.net.ssl.trustStorePassword", "passphrase");
    }

    private static final String MESSAGE = "The quick brown fox jumps over the lazy dog";
    private static final int DELAY_IN_MILLISECONDS = 1000;
    private static final String[] PROTOCOLS = new String[] {"TLSv1.3"};
    private static final String[] CIPHER_SUITES = new String[] {"TLS_AES_128_GCM_SHA256"};

    public static void main(String[] args) throws Exception {

        try (EchoServer server = EchoServer.create()) {
            new Thread(server).start();
            Thread.sleep(DELAY_IN_MILLISECONDS);

            try (SSLSocket sslSocket = createSocket("localhost", server.port())) {
                InputStream is = new BufferedInputStream(sslSocket.getInputStream());
                OutputStream os = new BufferedOutputStream(sslSocket.getOutputStream());
                os.write(MESSAGE.getBytes());
                os.flush();

                byte[] data = new byte[2048];
                int length = is.read(data);
                if (length <= 0) throw new IOException("No data received.");
                System.out.printf("Client received %d bytes: %s%n", length, new String(data, 0, length));
            }
        }
    }

    public static SSLSocket createSocket(String host, int port) throws IOException {
        SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, port);
        socket.setEnabledProtocols(PROTOCOLS);
        socket.setEnabledCipherSuites(CIPHER_SUITES);
        return socket;
    }

    public static class EchoServer implements Runnable, AutoCloseable {

        private static final int FREE_PORT = 0;
        private final SSLServerSocket sslServerSocket;

        private EchoServer(SSLServerSocket sslServerSocket) { this.sslServerSocket = sslServerSocket; }

        public static EchoServer create() throws IOException {
            return create(FREE_PORT);
        }

        public static EchoServer create(int port) throws IOException {
            SSLServerSocket socket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(port);
            socket.setEnabledProtocols(PROTOCOLS);
            socket.setEnabledCipherSuites(CIPHER_SUITES);
            return new EchoServer(socket);
        }

        @Override
        public void run() {
            System.out.printf("Server started on port %d%n", port());

            try (SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept()) {
                System.out.println("Accepted client socket");
                InputStream is = new BufferedInputStream(sslSocket.getInputStream());
                OutputStream os = new BufferedOutputStream(sslSocket.getOutputStream());

                byte[] data = new byte[2048];
                int length = is.read(data);
                if (length <= 0) throw new IOException("No data received.");
                System.out.printf("Server received %d bytes: %s%n", length, new String(data, 0, length));
                os.write(data, 0, length);
                os.flush();
            } catch (Exception e) {
                System.out.printf("An exception occurred: %s%n", e.getMessage());
            }
            System.out.println("Server stopped.");
        }

        public int port() { return sslServerSocket.getLocalPort(); }

        @Override
        public void close() throws IOException {
            if (sslServerSocket != null && !sslServerSocket.isClosed()) {
                sslServerSocket.close();
            }
        }
    }
}
