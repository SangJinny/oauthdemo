package com.example.oauthdemo.config;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.DefaultSecurityContextAccessor;
import org.springframework.security.oauth2.provider.SecurityContextAccessor;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class XoauthRequestFactory extends DefaultOAuth2RequestFactory {

    private SecurityContextAccessor securityContextAccessor = new DefaultSecurityContextAccessor();

    public XoauthRequestFactory(ClientDetailsService clientDetailsService) {
        super(clientDetailsService);
        super.setCheckUserScopes(false); // scope를 사용하지 않음.
        System.out.println("XoauthRequestFactory CREATED!!");
    }

    @Override
    public void setSecurityContextAccessor(SecurityContextAccessor securityContextAccessor) {
        this.securityContextAccessor = securityContextAccessor;
        super.setSecurityContextAccessor(securityContextAccessor);
    }

    @Override
    public AuthorizationRequest createAuthorizationRequest(Map<String, String> authorizationParameters) {
        System.out.println("Authorization Request CALLED!!");
        return super.createAuthorizationRequest(authorizationParameters);
    }
}
