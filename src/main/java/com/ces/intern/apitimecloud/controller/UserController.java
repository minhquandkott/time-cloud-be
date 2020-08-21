package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.http.request.UserRequest;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping(value ="/")
    public UserResponse createUser(@RequestBody UserRequest userRequest)
    {
        return userService.save(userRequest);
    }

    @GetMapping("/{id}")
    public UserDTO findUser(@PathVariable Integer id)
    {
        return userService.findUser(id);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@RequestBody UserRequest userRequest, @PathVariable Integer id)
    {
        userRequest.setId(id);
        return userService.update(userRequest);
    }

    @DeleteMapping(value = "/")
    public void deleteUser(@RequestBody int[] ids)
    {
        userService.delete(ids);
    }
}
