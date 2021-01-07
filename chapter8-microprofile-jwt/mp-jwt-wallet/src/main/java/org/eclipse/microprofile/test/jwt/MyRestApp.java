package org.eclipse.microprofile.test.jwt;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.auth.LoginConfig;

// We set the authentication method to the MP-JWT for the MicroProfile JWT method
// the realmName maps the security-domains setting in the project-defaults.yml
@LoginConfig(authMethod = "MP-JWT", realmName = "jwt-jaspi")
@ApplicationScoped
@ApplicationPath("/wallet")
public class MyRestApp extends Application {
}
