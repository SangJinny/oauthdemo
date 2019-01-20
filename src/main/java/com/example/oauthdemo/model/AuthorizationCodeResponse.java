package com.example.oauthdemo.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class AuthorizationCodeResponse implements Serializable {
    private final String status;
    private final Map<String, String> redirect_uri;

    public AuthorizationCodeResponse(String code) {
        status = "redirect";
        redirect_uri = new HashMap<>();
        redirect_uri.put("action", "show");
        redirect_uri.put("code", code);
    }
}
