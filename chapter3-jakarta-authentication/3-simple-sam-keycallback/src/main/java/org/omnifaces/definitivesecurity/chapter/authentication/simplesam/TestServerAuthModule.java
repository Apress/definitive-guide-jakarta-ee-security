package org.omnifaces.definitivesecurity.chapter.authentication.simplesam;

import static jakarta.security.auth.message.AuthStatus.SEND_SUCCESS;
import static jakarta.security.auth.message.AuthStatus.SUCCESS;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertStore;
import java.util.Arrays;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import jakarta.security.auth.message.AuthException;
import jakarta.security.auth.message.AuthStatus;
import jakarta.security.auth.message.MessageInfo;
import jakarta.security.auth.message.MessagePolicy;
import jakarta.security.auth.message.callback.CallerPrincipalCallback;
import jakarta.security.auth.message.callback.CertStoreCallback;
import jakarta.security.auth.message.callback.GroupPrincipalCallback;
import jakarta.security.auth.message.callback.PrivateKeyCallback;
import jakarta.security.auth.message.callback.SecretKeyCallback;
import jakarta.security.auth.message.callback.TrustStoreCallback;
import jakarta.security.auth.message.module.ServerAuthModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * @author Arjan Tijms
 * 
 */
public class TestServerAuthModule
    implements ServerAuthModule {

    private CallbackHandler handler;

    @Override
    public Class<?>[] getSupportedMessageTypes() {
        return new Class[] {
            HttpServletRequest.class,
            HttpServletResponse.class };
    }

    @Override
    public void initialize(
        MessagePolicy requestPolicy,
        MessagePolicy responsePolicy,
        CallbackHandler handler,
        @SuppressWarnings("rawtypes") Map options)
        throws AuthException {
        this.handler = handler;
    }

    @Override
    public AuthStatus validateRequest(
        MessageInfo messageInfo,
        Subject clientSubject,
        Subject serviceSubject)
        throws AuthException {
        try {
            
            
            // ### Get keystore (certstore) and truststore ###
            
            CertStoreCallback certStoreCallback = 
                new CertStoreCallback();
            handler.handle(new Callback[] { certStoreCallback });
            CertStore certStore = 
                certStoreCallback.getCertStore();
    
            TrustStoreCallback trustStoreCallback = 
                new TrustStoreCallback();
            handler.handle(new Callback[] { trustStoreCallback });
            KeyStore trustStore = 
                trustStoreCallback.getTrustStore();
            
            
            
            // ### Get secret key ###
            
            SecretKeyCallback secretKeyCallback = new SecretKeyCallback(
                new SecretKeyCallback.AliasRequest(
                    "foo"));
            handler.handle(new Callback[] {
                secretKeyCallback });
            
            if (secretKeyCallback.getKey() != null) {
                // Use key/token to access remote identity provider
                
            }
            
            
            //     ### Private key by Alias ###
            
            PrivateKeyCallback privateKeyCallback = new PrivateKeyCallback(
                new PrivateKeyCallback.AliasRequest(
                    "s1as"));
            
            handler.handle(new Callback[] {
                privateKeyCallback });
            
            PrivateKey privateKey = privateKeyCallback
                .getKey();
                    
            
            
            //  ### Get private key ### 
            
            
            //     ### Private key by Subject key identifier ### 
            
            byte[] subjectKeyIdentifier = new BigInteger(
                "36 1D 53 7B AF 4A 8A 19   F2 DD FE EC 63 5E 6D 25   FE 71 AB DF "
                .replace(" ", ""),
                16).toByteArray();
            if (subjectKeyIdentifier.length > 20) {
                subjectKeyIdentifier = Arrays.copyOfRange(
                    subjectKeyIdentifier, 1,
                    subjectKeyIdentifier.length - 1);
            }
            
            privateKeyCallback = new PrivateKeyCallback(
                new PrivateKeyCallback.SubjectKeyIDRequest(
                    subjectKeyIdentifier));
            
            handler.handle(new Callback[] {
                privateKeyCallback });
            
            privateKey = privateKeyCallback
                .getKey();
            
            
            //    ### Private key default
            
            privateKeyCallback = new PrivateKeyCallback(
                null);
            
            handler.handle(new Callback[] {
                privateKeyCallback });
            
            privateKey = privateKeyCallback
                .getKey();
            
        
    
            handler.handle(new Callback[] {
                // Name of the authenticated user
                new CallerPrincipalCallback(
                    clientSubject, "jsmith"),
                // Groups of the authenticated user
                
                new GroupPrincipalCallback(
                    clientSubject,
                    new String[] { "user" }) });

        } catch (IOException
            | UnsupportedCallbackException e) {
            throw (AuthException) 
                new AuthException().initCause(e);
        }

        return SUCCESS;
    }

    @Override
    public AuthStatus secureResponse(
        MessageInfo messageInfo,
        Subject serviceSubject)
        throws AuthException {
        return SEND_SUCCESS;
    }

    @Override
    public void cleanSubject(
        MessageInfo messageInfo, Subject subject)
        throws AuthException {
    }
}