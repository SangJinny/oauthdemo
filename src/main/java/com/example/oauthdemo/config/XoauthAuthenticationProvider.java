package com.example.oauthdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Component
public class XoauthAuthenticationProvider extends AnonymousAuthenticationProvider {
    @Autowired
    private HttpServletRequest request;

    public XoauthAuthenticationProvider() {
        super("xoauthKey");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String ipAddress = request.getRemoteAddr();

        System.out.println("@@@@@@@ "+ipAddress);
        System.out.println("@@@@@@@ "+authentication.getDetails());

        /*final List<GrantedAuthority> grantedAuths = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        grantedAuths.add(grantedAuthority);*/

        AnonymousAuthenticationToken result = new AnonymousAuthenticationToken("xoauthKey","xoauthPrincipal", new ArrayList<>());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
