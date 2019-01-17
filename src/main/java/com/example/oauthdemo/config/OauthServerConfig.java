package com.example.oauthdemo.config;

import com.example.oauthdemo.interceptor.XoauthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
                .redirectUris("http://localhost:8089/")
        ;
    }

    // EndPoint 설정
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .pathMapping("/oauth/authorize", "/xoauth/authorize")
                .pathMapping("/oauth/token", "/xoauth/token")
                .addInterceptor(xoauthInterceptor)
                .requestFactory(xoauthRequestFactory)
                .tokenStore(inmemoryTokenStore)
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
}
