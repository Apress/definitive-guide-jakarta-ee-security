package org.omnifaces.definitivesecurity.chapter.authentication.simplesam;

import static jakarta.security.auth.message.AuthStatus.SEND_FAILURE;
import static jakarta.security.auth.message.AuthStatus.SEND_SUCCESS;
import static jakarta.security.auth.message.AuthStatus.SUCCESS;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
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
import jakarta.security.auth.message.callback.GroupPrincipalCallback;
import jakarta.security.auth.message.module.ServerAuthModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * @author Arjan Tijms
 * 
 */
public class BasicServerAuthModule
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

            HttpServletRequest request = (HttpServletRequest) 
              messageInfo.getRequestMessage();
            HttpServletResponse response = (HttpServletResponse) 
              messageInfo.getResponseMessage();

            String[] credentials = getCredentials(request);
            
            if (credentials != null) {

                // Stand-in for real lookup
                TestIdentityStore identityStore 
                    = new TestIdentityStore();

                Map<String, ?> validation = identityStore
                    .validate(credentials[0],
                        credentials[1]);

                if ((Boolean) validation
                        .get("outcome")) {
                    handler
                        .handle(new Callback[] {
                            // Name of the authenticated user
                            new CallerPrincipalCallback(
                                clientSubject,
                                (String) validation
                                    .get("callerName")),

                            // Groups of the authenticated user
                            new GroupPrincipalCallback(
                                clientSubject,
                                (String[]) validation
                                    .get("groups")) });

                    return SUCCESS;
                }
            }

            if (isProtectedResource(
                messageInfo)) {
                response.setHeader(
                    "WWW-Authenticate",
                    String.format(
                        "Basic realm=\"%s\"",
                        "test"));
                response.sendError(
                    SC_UNAUTHORIZED);

                return SEND_FAILURE;
            }

            handler.handle(new Callback[] {
                new CallerPrincipalCallback(
                    clientSubject,
                    (Principal) null) });
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

    private String[] getCredentials(
        HttpServletRequest request) {

        String authorizationHeader = 
            request.getHeader("Authorization");
        if (authorizationHeader != null && 
            authorizationHeader.startsWith("Basic ")) {
            return new String(
                Base64.getDecoder()
                    .decode(authorizationHeader
                        .substring(6)))
                            .split(":");
        }

        return null;
    }

    public static boolean isProtectedResource(
        MessageInfo messageInfo) {
        return Boolean.valueOf(
            (String) messageInfo.getMap().get(
            "jakarta.security.auth.message.MessagePolicy.isMandatory"
        ));
    }
}