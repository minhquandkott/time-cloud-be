package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.request.UserRequest;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse save(UserRequest userRequest) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity user = modelMapper.map(userRequest, UserEntity.class);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        user = userRepository.save(user);
        return userResponse;
    }

    @Override
    public UserDTO findUser(Integer id) {
        UserEntity user = userRepository.getOne(id);
        ModelMapper modelMapper = new ModelMapper();
        UserDTO response = modelMapper.map(user, UserDTO.class);
        return response;
    }

    @Override
    public UserDTO update(UserRequest userRequest) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = userRepository.getOne(userRequest.getId());
        userEntity = modelMapper.map(userRequest, UserEntity.class);
        userEntity = userRepository.save(userEntity);
        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
        return userDTO;
    }

    @Override
    public void delete(int[] ids) {
        for(int item : ids)
        {
            userRepository.deleteById(item);
        }
    }
}
