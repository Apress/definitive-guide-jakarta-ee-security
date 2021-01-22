package com.apress.chapter6.jaas.consolemenu;

import java.security.BasicPermission;

public class CustomResourcePermission extends BasicPermission {
    public CustomResourcePermission(String name) {
        super(name);
    }
}