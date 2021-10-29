package org.omnifaces.definitivesecurity.chapter.authentication.simplesam;

import static jakarta.security.auth.message.AuthStatus.SEND_SUCCESS;
import static jakarta.security.auth.message.AuthStatus.SUCCESS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import jakarta.security.auth.message.AuthException;
import jakarta.security.auth.message.AuthStatus;
import jakarta.security.auth.message.MessageInfo;
import jakarta.security.auth.message.MessagePolicy;
import jakarta.security.auth.message.MessagePolicy.ProtectionPolicy;
import jakarta.security.auth.message.MessagePolicy.TargetPolicy;
import jakarta.security.auth.message.callback.CallerPrincipalCallback;
import jakarta.security.auth.message.callback.GroupPrincipalCallback;
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

            HttpServletRequest request = (HttpServletRequest) messageInfo
                .getRequestMessage();

            handler.handle(new Callback[] {
                new CallerPrincipalCallback(
                    clientSubject, "jsmith"),

                new GroupPrincipalCallback(
                    clientSubject,
                    new String[] { "user" }) });

            messageInfo.setRequestMessage(
                new TestHttpServletRequestWrapper(
                    request));

            return SUCCESS;

        } catch (IOException
            | UnsupportedCallbackException e) {
            throw (AuthException) new AuthException()
                .initCause(e);
        }
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

    public static MessagePolicy getMessagePolicy(
        boolean mandatory) {
    
        List<TargetPolicy> targetPolicies = 
            new ArrayList<>();
    
        targetPolicies.add(new TargetPolicy(
            null, // No Target
            new ProtectionPolicy() {
                public String getID() {
                    return ProtectionPolicy
                        .AUTHENTICATE_SENDER;
                }
            }));
    
        return new MessagePolicy(
            targetPolicies.toArray(
                new TargetPolicy[
                    targetPolicies
                        .size()]),
            mandatory);
    }

}