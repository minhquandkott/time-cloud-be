package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.request.UserRequest;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements com.ces.intern.apitimecloud.service.UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String save(UserRequest userRequest) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity user = modelMapper.map(userRequest, UserEntity.class);
        user = userRepository.save(user);
        return "Tạo tài khoản thành công";
    }

    @Override
    public UserResponse findUser(Integer id) {
        UserEntity user = userRepository.getOne(id);
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        UserResponse userResponse = modelMapper.map(userDTO, UserResponse.class);
        return userResponse;
    }

    @Override
    public UserResponse update(UserRequest userRequest, Integer id) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(userRequest, UserDTO.class);
        userDTO.setId(id);
        UserEntity userEntity = userRepository.findById(id).get();
        userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity = userRepository.save(userEntity);
        UserResponse userResponse = modelMapper.map(userDTO, UserResponse.class);
        return userResponse;
    }

    @Override
    public void delete(int[] ids) {
        for(int item : ids)
        {
            userRepository.deleteById(item);
        }
    }
}
