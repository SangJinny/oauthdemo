package com.example.oauthdemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Component
public class XoauthAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Autowired
    private OAuth2RequestFactory xoauthRequestFactory;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("@@ custom ENTRY POINT called!!");

        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> convertedParameters = new HashMap<>();
        requestParams.forEach((k,v) -> {
            convertedParameters.put(k,Arrays.stream(v).findFirst().orElse(""));
        });

        AuthorizationRequest authorizationRequest = xoauthRequestFactory.createAuthorizationRequest(convertedParameters);
        OAuth2Authentication combinedAuth = new OAuth2Authentication(authorizationRequest.createOAuth2Request(), createAuthentication(request));
        String authorizationCode = authorizationCodeServices.createAuthorizationCode(combinedAuth);

        Map<String, Object> responseObject = new HashMap<>();
        Map<String, String> responseObject2 = new HashMap<>();
        responseObject2.put("redirect_uri", convertedParameters.get("redirect_uri"));
        responseObject2.put("code", authorizationCode);
        responseObject.put("code",responseObject2);
        responseObject.put("status","redirect");

        String responseJson = new ObjectMapper().writeValueAsString(responseObject);
        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        printWriter.print(responseJson);
        printWriter.flush();
    }

    private Authentication createAuthentication(HttpServletRequest request) {
        AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add((GrantedAuthority) () -> "ROLE_PUBLIC");

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                "xoauthu", "xoauthp", authorities);
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

        return authRequest;
    }
}
