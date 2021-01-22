package com.apress.chapter6.jaas;

import java.io.Serializable;
import java.security.Principal;

public class SimpleUserPrincipal implements Principal, Serializable {

    private final String name;

    public SimpleUserPrincipal(String name) {
        if (name == null) {
            throw new NullPointerException("Illegal null input for user Principal.");
        }
        this.name = name;
    }

    public String toString() {
        return ("MyUserPrincipal: " + name);
    }

    @Override
    public String getName() {
       return name;
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof SimpleUserPrincipal))
            return false;
        SimpleUserPrincipal that = (SimpleUserPrincipal) o;

        return this.getName().equals(that.getName());
    }

    public int hashCode() {
        return name.hashCode();
    }
}
