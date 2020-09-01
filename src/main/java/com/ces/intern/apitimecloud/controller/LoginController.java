package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.exception.LoginUserException;
import com.ces.intern.apitimecloud.http.request.UserLoginRequest;
import com.ces.intern.apitimecloud.http.response.ErrorResponse;
import com.ces.intern.apitimecloud.security.config.SecurityContact;
import com.ces.intern.apitimecloud.service.UserService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.Utils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class LoginController {


    private final UserService userService;

    public LoginController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest user){

        if(user.getEmail() == "" || user.getPassword() == ""){
            throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage());
        }
        UserDTO loginUser = userService.validateUser(user.getEmail(), user.getPassword());

        String token = Utils.generateJWTToken(loginUser);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add(SecurityContact.HEADER_STRING, SecurityContact.TOKEN_PREFIX + token);

        return new ResponseEntity<>("OK", responseHeader, HttpStatus.OK);
    }

    @ExceptionHandler({LoginUserException.class})
    public ResponseEntity<Object> LoginError(Exception ex, HttpServletRequest request){
        ErrorResponse response = new ErrorResponse();
        response.setError(ExceptionMessage.USERNAME_PASSWORD_INVALIDATE.getMessage());
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURL().toString());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(new Date());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND );
    }
}
