package com.apress.chapter6.jaas.consolemenu;

import com.apress.chapter6.jaas.SimpleCallbackHandler;
import com.apress.chapter6.jaas.ReadEnvVariablesAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.PrivilegedAction;

public class JaasAuthorization {
    public enum Action { action1, action2, action3, logout }

    public static void main(String[] args) throws LoginException, IOException {
        JaasAuthorization jaasAuthorization = new JaasAuthorization();
        LoginContext loginContext = null;

        while (true) {
            loginContext = new LoginContext("SimpleJAASApp", new SimpleCallbackHandler());
            loginContext.login();

            boolean flag = true;
            while (flag)
                flag = jaasAuthorization.performAction(loginContext);
        }
    }

    boolean performAction(LoginContext loginContext) throws IOException, LoginException {
        boolean flag = true;
        System.out.println("Please specify action to take (usage: action1, action2, action3, logout)");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            switch (Action.valueOf(br.readLine())) {
                case action1:
                    PrivilegedAction customResourceAction = new CustomResourceAction();
                    Subject.doAsPrivileged(loginContext.getSubject(), customResourceAction, null);
                    System.out.println("action1 was performed by " + loginContext.getSubject().getPrincipals().iterator().next().getName());
                    break;
                case action2:
                    PrivilegedAction readEnvVariablesAction = new ReadEnvVariablesAction();
                    Subject.doAsPrivileged(loginContext.getSubject(), readEnvVariablesAction, null);
                    System.out.println("action2 was performed by " + loginContext.getSubject().getPrincipals().iterator().next().getName());
                    break;
                case action3:
                    PrivilegedAction readFileAction = new ReadFileAction();
                    Subject.doAsPrivileged(loginContext.getSubject(), readFileAction, null);
                    System.out.println("action3 was performed by " + loginContext.getSubject().getPrincipals().iterator().next().getName());
                    break;
                case logout:
                    loginContext.logout();
                    System.out.println("You have been logged out.");
                    flag = false;
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid entry. Please try again...");
        }
        return flag;
    }
}