package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.exception.LoginUserException;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.http.request.UserRequest;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        return "Tạo tài khoản thành công";
    }

    @Override
    public UserResponse findUser(Integer id) {
        UserEntity user = userRepository.getOne(id);

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        UserResponse userResponse = modelMapper.map(userDTO, UserResponse.class);
        return userResponse;
    }

    @Override
    public UserResponse update(UserRequest userRequest, Integer id) {

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

    @Override
    public UserDTO findByEmail(String email) {
        UserEntity user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()
                                                            + " with email " + email));
        UserDTO returnValue = modelMapper.map(user, UserDTO.class);
        return returnValue;
    }

    @Override
    public List<UserDTO> getAllByCompanyId(Integer companyId) {
        List<UserEntity> userEntities = new ArrayList<>();

        userEntities = userRepository.getAllByCompanyId(companyId);
        if(userEntities.size() == 0) throw  new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()
                + " with " +companyId);

        return userEntities.stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<UserDTO> getAllByCompanyAndRole(Integer companyId, Integer role) {
        List<UserEntity> userEntities = new ArrayList<>();

        userEntities = userRepository.getAllByCompanyId(companyId);
        if(userEntities.size() == 0) throw  new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()
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

        UserDTO returnValue = modelMapper.map(user, UserDTO.class);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Not Found"));

        return User.builder().username(user.getEmail()).password(user.getPassword()).roles().build();
    }
}
