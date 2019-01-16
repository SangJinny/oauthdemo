package com.example.oauthdemo.config;

import com.example.oauthdemo.filter.CustomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SpringSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("xoauthAuthenticationEntryPoint")
    private AuthenticationEntryPoint xoauthAuthenticationEntryPoint;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
                .addFilterBefore(new CustomFilter(), AnonymousAuthenticationFilter.class)
            .exceptionHandling().authenticationEntryPoint(xoauthAuthenticationEntryPoint)
        ;
    }
}
