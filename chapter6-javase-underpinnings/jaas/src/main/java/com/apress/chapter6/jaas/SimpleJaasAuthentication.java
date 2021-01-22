package com.apress.chapter6.jaas;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

public class SimpleJaasAuthentication {

    public static void main(String[] args) throws LoginException {
        LoginService loginService = new LoginService();
        Subject subject = loginService.loginSubject();

        System.out.println(subject.getPrincipals().iterator().next() + " successfully logged in"
                + " and has " + subject.getPublicCredentials().size() + " Public Credential(s)"
                + " and " + subject.getPrivateCredentials().size() + " Private Credential(s)");
    }
}
