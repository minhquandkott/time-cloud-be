package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.http.request.UserRequest;
import com.ces.intern.apitimecloud.http.response.UserResponse;

public interface IUserService {
    public UserResponse save(UserRequest userRequest);
}
