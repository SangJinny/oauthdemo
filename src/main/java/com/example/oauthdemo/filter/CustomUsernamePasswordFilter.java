package com.example.oauthdemo.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomUsernamePasswordFilter extends AbstractAuthenticationProcessingFilter {
    public CustomUsernamePasswordFilter() {
        super(new AntPathRequestMatcher("/xoauth/authorize", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                "xoauthu", "xoauthp");
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        logger.info(authRequest.toString());
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
