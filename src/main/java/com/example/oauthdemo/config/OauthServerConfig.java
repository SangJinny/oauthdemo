package com.example.oauthdemo.config;

import com.example.oauthdemo.interceptor.XoauthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;


@Configuration
@EnableAuthorizationServer
public class OauthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore inmemoryTokenStore;

    @Autowired
    private OAuth2RequestFactory xoauthRequestFactory;

    @Autowired
    private XoauthInterceptor xoauthInterceptor;

    @Autowired
    @Qualifier("xoauthAuthenticationEntryPoint")
    private AuthenticationEntryPoint xoauthAuthenticationEntryPoint;


    // Client에 대한 설정.
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("foo")
                .secret("bar")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("public")
                //.autoApprove(true)
                .redirectUris("/urn:ietf:wg:oauth:2.0:oob:auto")
        ;
    }

    // EndPoint 설정
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .pathMapping("/oauth/authorize", "/xoauth/authorize")
                .pathMapping("/oauth/token", "/xoauth/token")
                .authorizationCodeServices(authorizationCodeServices())
                .addInterceptor(xoauthInterceptor)
                .requestFactory(xoauthRequestFactory)
                .tokenStore(tokenStore())
                .tokenServices(tokenService())
        .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                ;
                //.authenticationManager(authenticationManager);
    }

    // oauth 인증 서버에 대한 설정.
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("permitAll()")
                .authenticationEntryPoint(xoauthAuthenticationEntryPoint)
        ;
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenService() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
}
