package com.example.oauthdemo.config;

import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class XoauthRequestFactory extends DefaultOAuth2RequestFactory {

    //private final ClientDetailsService clientDetailsService;
    private SecurityContextAccessor securityContextAccessor = new DefaultSecurityContextAccessor();

    public XoauthRequestFactory(ClientDetailsService clientDetailsService) {
        super(clientDetailsService);
        super.setCheckUserScopes(false); // scope를 사용하지 않음.
        System.out.println("@@ custom REQUEST FACTORY created!!");
    }

    @Override
    public void setSecurityContextAccessor(SecurityContextAccessor securityContextAccessor) {
        this.securityContextAccessor = securityContextAccessor;
        super.setSecurityContextAccessor(securityContextAccessor);
    }

    public AuthorizationRequest createAuthorizationRequest(Map<String, String> authorizationParameters) {
        System.out.println("@@ custom createAuthorizationRequest called!!");
        return super.createAuthorizationRequest(authorizationParameters);
    }
}
