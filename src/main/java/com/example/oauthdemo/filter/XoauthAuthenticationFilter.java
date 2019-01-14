package com.example.oauthdemo.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class XoauthAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public XoauthAuthenticationFilter() {
        super();
    }

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                "xoauthUser", "xoauthPassword");

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        logger.info(SecurityContextHolder.getContext().getAuthentication());
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /*public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(
                    createAuthentication((HttpServletRequest) req));

                logger.info("Populated SecurityContextHolder with anonymous token: '"
                        + SecurityContextHolder.getContext().getAuthentication() + "'");
        }
        else {
                logger.info("SecurityContextHolder not populated with anonymous token, as it already contained: '"
                        + SecurityContextHolder.getContext().getAuthentication() + "'");
        }

        chain.doFilter(req, res);
    }*/
}
