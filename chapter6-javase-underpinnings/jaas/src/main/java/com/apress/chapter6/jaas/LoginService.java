package com.apress.chapter6.jaas;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class LoginService {

    public Subject loginSubject() throws LoginException {
        LoginContext loginContext = new LoginContext("SimpleJAASApp", new SimpleCallbackHandler());
        loginContext.login();

        // return the authenticated Subject
        return loginContext.getSubject();
    }
}
