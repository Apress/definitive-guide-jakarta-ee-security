package com.apress.chapter6.jaas;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import java.security.PrivilegedAction;

public class SimpleJaasAuthorization {

    public static void main(String[] args) throws LoginException {
        LoginService loginService = new LoginService();
        Subject subject = loginService.loginSubject();

        PrivilegedAction privilegedAction = new ReadEnvVariablesAction();
        Subject.doAsPrivileged(subject, privilegedAction, null);
    }
}
