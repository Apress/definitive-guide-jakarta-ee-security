package org.omnifaces.definitivesecurity.chapter.authentication.simplesam;

import static java.lang.Boolean.TRUE;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * 
 * @author Arjan Tijms
 * 
 */
public class TestHttpServletRequestWrapper
    extends HttpServletRequestWrapper {

    public TestHttpServletRequestWrapper(
        HttpServletRequest request) {
        super(request);
    }

    @Override
    public Object getAttribute(String name) {

        if ("isWrapped".equals(name)) {
            return TRUE;
        }

        return super.getAttribute(name);
    }

}
