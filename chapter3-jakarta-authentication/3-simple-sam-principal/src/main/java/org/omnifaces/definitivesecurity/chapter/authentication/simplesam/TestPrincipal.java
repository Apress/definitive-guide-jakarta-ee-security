package org.omnifaces.definitivesecurity.chapter.authentication.simplesam;

import java.security.Principal;

/**
 * 
 * @author Arjan Tijms
 * 
 */
public class TestPrincipal implements Principal {

    private final String name;
    private final int age;

    public TestPrincipal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

}
