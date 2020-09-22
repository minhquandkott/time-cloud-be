package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.exception.LoginUserException;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.http.request.UserRequest;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.ResponseMessage;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements com.ces.intern.apitimecloud.service.UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder){
        this.userRepository= userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String save(UserRequest userRequest) {

        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        UserEntity user = modelMapper.map(userRequest, UserEntity.class);
        user.setPassword(encodedPassword);

        user = userRepository.save(user);
        return ResponseMessage.CREATE_SUCCESS;
    }

    @Override
    public UserResponse findUser(Integer id) {
        UserEntity user = userRepository.getOne(id);

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return modelMapper.map(userDTO, UserResponse.class);
    }

    @Override
    public UserResponse update(UserRequest userRequest, Integer id) {

        UserDTO userDTO = modelMapper.map(userRequest, UserDTO.class);
        userDTO.setId(id);
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()));
        userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity = userRepository.save(userEntity);

        return modelMapper.map(userDTO, UserResponse.class);
    }

    @Override
    public void delete(int[] ids) {
        for(int item : ids)
        {
            userRepository.deleteById(item);
        }
    }

    @Override
    public UserDTO findByEmail(String email) {
        UserEntity user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()
                                                            + " with email " + email));

        return  modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllByCompanyId(Integer companyId) {
        List<UserEntity> userEntities = userRepository.getAllByCompanyId(companyId);
        if(userEntities.size() == 0) throw  new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()
                + " with " +companyId);

        return userEntities.stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<UserDTO> getAllByCompanyAndRole(Integer companyId, String role) {

        List<UserEntity> userEntities = userRepository.getAllByCompanyIdAndRole(companyId, role);
        if(CollectionUtils.isEmpty(userEntities)) throw  new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()
                + " with company " +companyId + " and role " +role);

        return userEntities.stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO validateUser(String email, String password) {


        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new LoginUserException(ExceptionMessage.USERNAME_PASSWORD_INVALIDATE.getMessage()));
        boolean validate = passwordEncoder.matches(password, user.getPassword());

        if(!validate) throw new LoginUserException(ExceptionMessage.USERNAME_PASSWORD_INVALIDATE.getMessage());

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Not Found"));

        return User.builder().username(user.getEmail()).password(user.getPassword()).roles().build();
    }
}
