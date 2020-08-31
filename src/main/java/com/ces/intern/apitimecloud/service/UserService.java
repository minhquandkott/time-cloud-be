package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.http.request.UserRequest;
import com.ces.intern.apitimecloud.http.response.UserResponse;

public interface UserService {
    public String save(UserRequest userRequest);
    public UserResponse findUser(Integer id);
    public UserResponse update(UserRequest userRequest, Integer id);
    public void delete(int[] ids);
}
