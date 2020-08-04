package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping(value ="/user")
    public UserDTO createUser(@RequestBody UserDTO userDTO)
    {
        return userService.save(userDTO);
    }
}
