package org.omnifaces.definitivesecurity.chapter.security.simplemechanisminteract;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ApplicationScoped
public class TestAuthenticationMechanism
    implements HttpAuthenticationMechanism {

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
            if (name.equals("john")
                && password.equals("password")) {
                return httpMessageContext
                    .notifyContainerAboutLogin(
                        "jsmith", Set.of("user"));
            }

            return httpMessageContext
                .responseUnauthorized();
        }

        return httpMessageContext.doNothing();
    }

}
