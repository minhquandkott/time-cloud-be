package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.exception.LoginUserException;
import com.ces.intern.apitimecloud.http.request.UserLoginRequest;
import com.ces.intern.apitimecloud.http.response.ErrorResponse;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.security.config.SecurityContact;
import com.ces.intern.apitimecloud.service.UserService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
public class LoginController {


    private final UserService userService;
    private final ModelMapper modelMapper;

    public LoginController(UserService userService,
                           ModelMapper modelMapper){
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserLoginRequest user){

        if(StringUtils.isEmpty(user.getEmail())|| StringUtils.isEmpty(user.getPassword())){
            throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage());
        }
        UserDTO loginUser = userService.validateUser(user.getEmail(), user.getPassword());

        List<String> info = Utils.generateJWTToken(loginUser);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add(SecurityContact.HEADER_STRING, SecurityContact.TOKEN_PREFIX + info.get(0));
        responseHeader.add(SecurityContact.HEADER_USERID, info.get(1));
        UserResponse userResponse = modelMapper.map(loginUser, UserResponse.class);

        return new ResponseEntity<UserResponse>(userResponse, responseHeader, HttpStatus.OK);
    }

    @ExceptionHandler({LoginUserException.class})
    public ResponseEntity<Object> loginError(Exception ex, HttpServletRequest request){
        ErrorResponse response = new ErrorResponse();
        response.setError(ExceptionMessage.USERNAME_PASSWORD_INVALIDATE.getMessage());
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURL().toString());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(new Date());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND );
    }
}
