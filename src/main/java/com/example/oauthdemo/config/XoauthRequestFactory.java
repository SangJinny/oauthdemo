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
    private ClientDetailsService clientDetailsService;
    public XoauthRequestFactory(ClientDetailsService clientDetailsService) {
        super(clientDetailsService);
        super.setCheckUserScopes(false); // scope를 사용하지 않음.

        this.clientDetailsService = clientDetailsService;
        System.out.println("@@ custom REQUEST FACTORY created!!");
    }

    @Override
    public void setSecurityContextAccessor(SecurityContextAccessor securityContextAccessor) {
        this.securityContextAccessor = securityContextAccessor;
        super.setSecurityContextAccessor(securityContextAccessor);
    }

    public AuthorizationRequest createAuthorizationRequest(Map<String, String> authorizationParameters) {
        System.out.println("@@ custom createAuthorizationRequest called!!");

        String clientId = authorizationParameters.get(OAuth2Utils.CLIENT_ID);
        String state = authorizationParameters.get(OAuth2Utils.STATE);
        String redirectUri = authorizationParameters.get(OAuth2Utils.REDIRECT_URI);
        Set<String> responseTypes = OAuth2Utils.parseParameterList(authorizationParameters
                .get(OAuth2Utils.RESPONSE_TYPE));

        Set<String> scopes = extractScopes(authorizationParameters, clientId);

        AuthorizationRequest request = new AuthorizationRequest(authorizationParameters,
                Collections.<String, String> emptyMap(), clientId, scopes, null, null, true, state, redirectUri,
                responseTypes);

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        request.setResourceIdsAndAuthoritiesFromClientDetails(clientDetails);

        return request;

        //return super.createAuthorizationRequest(authorizationParameters);
    }

    private Set<String> extractScopes(Map<String, String> requestParameters, String clientId) {
        Set<String> scopes = OAuth2Utils.parseParameterList(requestParameters.get(OAuth2Utils.SCOPE));
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if ((scopes == null || scopes.isEmpty())) {
            // If no scopes are specified in the incoming data, use the default values registered with the client
            // (the spec allows us to choose between this option and rejecting the request completely, so we'll take the
            // least obnoxious choice as a default).
            scopes = clientDetails.getScope();
        }

        // xoauth는 scope를 사용하지 않음.
        /*if (false) {
            scopes = checkUserScopes(scopes, clientDetails);
        }*/

        return scopes;
    }


}
