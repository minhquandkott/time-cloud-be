package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.http.request.UserLoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest user){
        return "Login Success !!!";
    }
}
