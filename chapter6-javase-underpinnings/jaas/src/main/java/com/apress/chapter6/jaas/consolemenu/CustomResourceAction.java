package com.apress.chapter6.jaas.consolemenu;

import java.security.PrivilegedAction;

public class CustomResourceAction implements PrivilegedAction {
    @Override
    public Object run() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new CustomResourcePermission("sample-resource"));
        }
        System.out.println("Hi, I'm CustomResourceAction and I have access to sample-resource!");
        return null;
    }
}
