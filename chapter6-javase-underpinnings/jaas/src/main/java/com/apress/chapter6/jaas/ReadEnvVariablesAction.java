package com.apress.chapter6.jaas;

import java.security.PrivilegedAction;

public class ReadEnvVariablesAction implements PrivilegedAction {

    public Object run() {
        System.out.println("\nYour JAVA.HOME environment variable is: "
                + System.getProperty("java.home"));

        System.out.println("\nYour USER.HOME environment variable is: "
                + System.getProperty("user.home"));

        return null;
    }
}