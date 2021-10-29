package org.omnifaces.definitivesecurity.chapter.security.simplemechanisminteract;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Arjan Tijms
 *
 */
@WebServlet(urlPatterns = "/servlet")
public class TestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

        response.getWriter().write(
            "Caller has name: " +
            (request.getUserPrincipal() != null
                ? request.getUserPrincipal()
                : "<unauthenticated>")
            + "\n" +
            "Caller has role \"user\": " +
            request.isUserInRole("user") +
            "\n");

    }

}
