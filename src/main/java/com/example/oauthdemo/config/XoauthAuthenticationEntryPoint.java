package com.example.oauthdemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class XoauthAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Autowired
    private OAuth2RequestFactory xoauthRequestFactory;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("@@ custom ENTRY POINT called!!");
        System.out.println("@@@ "+ SecurityContextHolder.getContext().getAuthentication());
        AuthorizationCodeServices authorizationCodeServices = new InMemoryAuthorizationCodeServices();


        Map<String, Object> responseObject = new HashMap<>();
        Map<String, String> responseObject2 = new HashMap<>();
        responseObject2.put("redirect_uri", "asdfasdf");
        responseObject.put("code",responseObject2);
        String responseJson = new ObjectMapper().writeValueAsString(responseObject);
        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        printWriter.print(responseJson);
        printWriter.flush();
    }
}
