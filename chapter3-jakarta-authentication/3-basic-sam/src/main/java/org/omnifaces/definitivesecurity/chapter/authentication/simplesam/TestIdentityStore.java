package org.omnifaces.definitivesecurity.chapter.authentication.simplesam;

import java.util.Map;

/**
 * 
 * @author Arjan Tijms
 * 
 */
public class TestIdentityStore {

    public Map<String, ?> validate(
        String callerName, String password) {
        return Map.of(
            "outcome", true, 
            "callerName", callerName, 
            "groups", new String[] { "user" });
    }

}
