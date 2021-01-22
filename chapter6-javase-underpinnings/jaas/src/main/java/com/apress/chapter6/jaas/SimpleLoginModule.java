package com.apress.chapter6.jaas;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Map;

public class SimpleLoginModule implements LoginModule {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "test";

    private Subject subject;
    private CallbackHandler callbackHandler;
    private boolean loginSucceeded = false;
    private SimpleUserPrincipal userPrincipal;

    @Override public void initialize(Subject subject, CallbackHandler callbackHandler,
            Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override public boolean login() throws LoginException {
        NameCallback nameCallback = new NameCallback("Username: ");
        PasswordCallback passwordCallback = new PasswordCallback("Password: ", false);
        try {
            callbackHandler.handle(new Callback[]{nameCallback, passwordCallback});
            String username = nameCallback.getName();
            String password = new String(passwordCallback.getPassword());

            if (USERNAME.equals(username) && PASSWORD.equals(password)) {
                userPrincipal = new SimpleUserPrincipal(username);
                System.out.println("Authentication succeeded!");
                loginSucceeded = true;
            }
            if (!loginSucceeded) throw new FailedLoginException("Authentication failure...");

        } catch (IOException | UnsupportedCallbackException e) {
            e.printStackTrace();
        }
        return loginSucceeded;
    }

    @Override public boolean commit() throws LoginException {
        if (!loginSucceeded) return false;

        subject.getPrincipals().add(userPrincipal);
        return true;
    }

    @Override public boolean abort() throws LoginException {
        logout();
        return true;
    }

    @Override public boolean logout() throws LoginException {
        subject.getPrincipals().remove(userPrincipal);
        subject = null;
        userPrincipal = null;
        return true;
    }
}
