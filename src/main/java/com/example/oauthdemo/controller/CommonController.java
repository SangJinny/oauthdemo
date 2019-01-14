package com.example.oauthdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {

    @RequestMapping("/errors")
    public ResponseEntity errorResponse() {
        return new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
