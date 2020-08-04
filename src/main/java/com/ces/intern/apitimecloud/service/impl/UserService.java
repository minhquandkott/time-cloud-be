package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.model.User;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO save(UserDTO userDTO) {
        return null;
    }
}
