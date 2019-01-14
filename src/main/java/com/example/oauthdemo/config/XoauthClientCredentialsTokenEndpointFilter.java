package com.example.oauthdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class XoauthClientCredentialsTokenEndpointFilter extends ClientCredentialsTokenEndpointFilter {

    private AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();


    public XoauthClientCredentialsTokenEndpointFilter() {
        super();
    }

    public XoauthClientCredentialsTokenEndpointFilter(String path) {
        super(path);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            throw new HttpRequestMethodNotSupportedException(request.getMethod(), new String[] { "POST" });
        }

        String clientId = request.getParameter("client_id");
        String clientSecret = request.getParameter("client_secret");
        System.out.println("@@@@  "+clientId+"  "+clientSecret);
        // If the request is already authenticated we can assume that this
        // filter is not needed
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication;
        }

        if (clientId == null) {
            throw new BadCredentialsException("No client credentials presented");
        }

        if (clientSecret == null) {
            clientSecret = "";
        }

        clientId = clientId.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(clientId,
                clientSecret);

        return this.getAuthenticationManager().authenticate(authRequest);

    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request,
                                             HttpServletResponse response) {
        return true;
    }

}
