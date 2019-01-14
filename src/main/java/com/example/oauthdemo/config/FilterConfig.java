package com.example.oauthdemo.config;

import com.example.oauthdemo.filter.XoauthAuthenticationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    /*@Bean
    public FilterRegistrationBean securityFilterChain(
            @Qualifier(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME) Filter securityFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
        registration.setOrder(Integer.MAX_VALUE);
        registration.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
        return registration;
    }*/

    /*@Bean
    public FilterRegistrationBean customFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        XoauthAuthenticationFilter customFilter = new XoauthAuthenticationFilter();
        registrationBean.setFilter(customFilter);
        registrationBean.setOrder(Integer.MIN_VALUE);
        return registrationBean;
    }*/

}
