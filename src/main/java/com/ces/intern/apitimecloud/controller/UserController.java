package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.http.request.UserRequest;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value ="")
    public String createUser(@RequestBody UserRequest userRequest)
    {
        return userService.save(userRequest);
    }

    @GetMapping("/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public UserResponse findUser(@PathVariable Integer id)
    {
        return userService.findUser(id);
    }

    @PutMapping("/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public UserResponse updateUser(@RequestBody UserRequest userRequest, @PathVariable Integer id)
    {
        return userService.update(userRequest, id);
    }

    @DeleteMapping(value = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public String deleteUser(@RequestBody int[] ids)
    {
        userService.delete(ids);
        return "Xóa thành công";
    }
}
