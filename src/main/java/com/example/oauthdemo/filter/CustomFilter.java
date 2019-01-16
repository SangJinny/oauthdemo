package com.example.oauthdemo.filter;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomFilter implements Filter {

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(
                    createAuthentication((HttpServletRequest) req));
        }
        System.out.println("@@ "+ SecurityContextHolder.getContext().getAuthentication());
        chain.doFilter(req, res);
    }

    public Authentication createAuthentication(HttpServletRequest request) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_PUBLIC";
            }
        });
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                "xoauthu", "xoauthp", authorities);
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        //authRequest.setAuthenticated(true);

        return authRequest;
    }
}
