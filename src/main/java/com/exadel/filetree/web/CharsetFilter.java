package com.exadel.filetree.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: naXa!
 * Date: 05.07.13
 * Time: 9:57
 */
public class CharsetFilter implements Filter {
    final private String charset = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Sets up character set <code>charset</code> for Views.
     * @param request
     * @param response
     * @param chain chain of filters
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest)request;
        final HttpServletResponse resp = (HttpServletResponse)response;
        req.setCharacterEncoding( charset );
        resp.setCharacterEncoding( charset );
        resp.setContentType( "text/html; charset=" + charset );

        chain.doFilter( request, response );
    }

    @Override
    public void destroy() {
    }
}
