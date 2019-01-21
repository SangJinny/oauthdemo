package com.example.oauthdemo.config;

import com.example.oauthdemo.filter.CustomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity(debug = true)
public class SpringSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("xoauthAuthenticationEntryPoint")
    private AuthenticationEntryPoint xoauthAuthenticationEntryPoint;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                //.addFilterBefore(new CustomFilter(), BasicAuthenticationFilter.class)
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(xoauthAuthenticationEntryPoint)
        ;
    }
}
