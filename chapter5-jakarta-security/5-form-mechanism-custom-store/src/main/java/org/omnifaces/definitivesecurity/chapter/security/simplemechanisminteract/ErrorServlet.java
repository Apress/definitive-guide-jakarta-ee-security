package org.omnifaces.definitivesecurity.chapter.security.simplemechanisminteract;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({ "/error-servlet" })
public class ErrorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        response.getWriter().write(
            """
             <html><body>
                 Login failed!
                 <a href="login-servlet">Try again</a>
             </body></html>
            """);
    }

}
