package com.example.oauthdemo.controller;

import com.example.oauthdemo.model.AuthorizationCodeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CommonController {

    @RequestMapping("/errors")
    public ResponseEntity errorResponse() {
        return new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping("/urn:ietf:wg:oauth:2.0:oob:auto")
    public ResponseEntity<AuthorizationCodeResponse> test(@RequestParam String code) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> redirect_uri = new HashMap<>();

        redirect_uri.put("action", "show");
        redirect_uri.put("code", code);

        response.put("status", "redirect");
        response.put("redirect_uri", redirect_uri);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
