package org.omnifaces.definitivesecurity.chapter.security.simplemechanisminteract;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

@ApplicationScoped
public class TestIdentityStore implements IdentityStore {

    public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {
        if (usernamePasswordCredential.compareTo("john", "password")) {
            return new CredentialValidationResult("john", Set.of("user"));
        }

        return INVALID_RESULT;
    }

}