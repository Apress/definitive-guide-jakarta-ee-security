package org.omnifaces.definitivesecurity.chapter.authentication.simplesam;

import jakarta.security.auth.message.config.AuthConfigFactory;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * 
 * @author Arjan Tijms
 * @author Werner Keil
 * 
 */
@WebListener
public class RegistrationListener
    implements ServletContextListener {

    @Override
    public void contextInitialized(
        ServletContextEvent sce) {
        AuthConfigFactory
        .getFactory()
        .registerServerAuthModule(
                new BasicServerAuthModule(),
                sce.getServletContext());
    }

}