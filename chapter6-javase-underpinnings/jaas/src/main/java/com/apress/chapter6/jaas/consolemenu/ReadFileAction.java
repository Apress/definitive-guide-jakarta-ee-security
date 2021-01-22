package com.apress.chapter6.jaas.consolemenu;

import java.io.File;
import java.security.PrivilegedAction;

public class ReadFileAction implements PrivilegedAction {

    public Object run() {
        File f = new File("foo.txt");
        System.out.print("\nfoo.txt does ");
        if (!f.exists())
            System.out.print("not ");
        System.out.println("exist in the current working directory.");
        return null;
    }
}