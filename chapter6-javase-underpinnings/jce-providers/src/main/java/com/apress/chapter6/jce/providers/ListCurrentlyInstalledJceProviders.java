package com.apress.chapter6.jce.providers;

import java.security.Provider;
import java.security.Security;

public class ListCurrentlyInstalledJceProviders {
    public static void main(String[] args) {

        for (Provider p : Security.getProviders()) {
            System.out.println("Name: " + p.getName() + p.getName().length() + " \tVersion: " + p.getVersionStr());
        }
    }
}
