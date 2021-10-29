package org.omnifaces.definitivesecurity.chapter.security.simplemechanisminteract;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.Status.VALID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ApplicationScoped
public class TestAuthenticationMechanism
    implements HttpAuthenticationMechanism {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Override
    public AuthenticationStatus validateRequest(
        HttpServletRequest request,
        HttpServletResponse response,
        HttpMessageContext httpMessageContext)
        throws AuthenticationException {

        String name = request
            .getParameter("name");
        String password = request
            .getParameter("password");

        if (name != null && password != null) {

            CredentialValidationResult result =
                identityStoreHandler.validate(
                    new UsernamePasswordCredential(
                        name, password));

            if (result.getStatus() == VALID) {
                return httpMessageContext
                    .notifyContainerAboutLogin(
                        result.getCallerPrincipal(),
                        result.getCallerGroups());
            }

            return httpMessageContext
                .responseUnauthorized();
        }

        return httpMessageContext.doNothing();
    }

}
