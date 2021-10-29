package org.omnifaces.definitivesecurity.chapter.authentication.simplesam;

import org.omnifaces.authenticationutils.AuthenticationUtils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * 
 * @author Arjan Tijms
 * 
 */
@WebListener
public class RegistrationListener
    implements ServletContextListener {

    @Override
    public void contextInitialized(
        ServletContextEvent sce) {
        AuthenticationUtils
            .registerServerAuthModule(
                new BasicServerAuthModule(),
                sce.getServletContext());
    }

}