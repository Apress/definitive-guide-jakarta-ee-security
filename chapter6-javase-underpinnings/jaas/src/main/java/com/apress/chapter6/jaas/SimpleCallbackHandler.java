package com.apress.chapter6.jaas;

import javax.security.auth.callback.*;
import java.io.Console;

public class SimpleCallbackHandler implements CallbackHandler {

    @Override
    public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
        Console console = System.console();
        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                NameCallback nameCallback = (NameCallback) callback;
                nameCallback.setName(console.readLine(nameCallback.getPrompt()));
            } else if (callback instanceof PasswordCallback) {
                PasswordCallback passwordCallback = (PasswordCallback) callback;
                passwordCallback.setPassword(console.readPassword(passwordCallback.getPrompt()));
            } else {
                throw new UnsupportedCallbackException(callback);
            }
        }
    }

}
