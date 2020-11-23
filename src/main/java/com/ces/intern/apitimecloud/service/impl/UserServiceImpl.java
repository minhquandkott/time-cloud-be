package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.ProjectUserDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.dto.UserRoleDTO;
import com.ces.intern.apitimecloud.entity.*;
import com.ces.intern.apitimecloud.http.exception.AlreadyExistException;
import com.ces.intern.apitimecloud.http.exception.LoginUserException;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.http.request.UserRequest;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.repository.*;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.ResponseMessage;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements com.ces.intern.apitimecloud.service.UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ProjectUserRepository projectUserRepository;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           UserRoleRepository userRoleRepository,
                           ProjectRepository projectRepository,
                           TaskRepository taskRepository,
                           ProjectUserRepository projectUserRepository){
        this.userRepository= userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.projectUserRepository = projectUserRepository;
    }

    @Override
    @Transactional
    public String save(UserRequest userRequest) {

        userRequest.setEmail(userRequest.getEmail().toLowerCase());

        if(userRepository.countByEmail(userRequest.getEmail()) == 1){
            throw new AlreadyExistException(ExceptionMessage.EMAIL_ALREADY_EXIST.getMessage());
        }else{
            String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

            UserEntity userEntity = modelMapper.map(userRequest, UserEntity.class);
            userEntity.setPassword(encodedPassword);
            Date date = new Date();
            userEntity.setBasicInfo(date, 1, date, 1);

            userEntity = userRepository.save(userEntity);
            userEntity.setCreatedBy(userEntity.getId());
            userEntity.setModifiedBy(userEntity.getId());
            userRepository.save(userEntity);

            return ResponseMessage.CREATE_SUCCESS;
        }

    }

    @Override
    public UserResponse findUser(Integer id) {
        UserEntity user = userRepository.getOne(id);

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return modelMapper.map(userDTO, UserResponse.class);
    }

    @Override
    public UserResponse update(UserRequest userRequest, Integer id, Integer modifiedBy) {

        UserDTO userDTO = modelMapper.map(userRequest, UserDTO.class);
        userDTO.setId(id);
        userDTO.setModifyAt(new Date());
        userDTO.setModifiedBy(modifiedBy);
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()));
        userDTO.setCreateAt(userEntity.getCreateAt());
        userDTO.setCreatedBy(userEntity.getCreatedBy());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setAvatar(userEntity.getAvatar());
        userEntity = modelMapper.map(userDTO, UserEntity.class);
        userRepository.save(userEntity);

        return modelMapper.map(userDTO, UserResponse.class);
    }

    @Override
    public void delete(Integer userId) {
        userRepository.deleteById(userId);
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
    public UserDTO validateUser(String email, String password) {


        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new LoginUserException(ExceptionMessage.USERNAME_PASSWORD_INVALIDATE.getMessage()));
        boolean validate = passwordEncoder.matches(password, user.getPassword());

        if(!validate) throw new LoginUserException(ExceptionMessage.USERNAME_PASSWORD_INVALIDATE.getMessage());

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<ProjectUserDTO> getAllByProjectId(Integer projectId) {

        projectRepository.findById(projectId)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+" with projectId"+projectId));
        return projectUserRepository
                .getAllByEmbedIdProjectId(projectId)
                .stream()
                .map(projectUserEntity -> modelMapper.map(projectUserEntity, ProjectUserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllByTaskId(Integer taskId) {
        taskRepository.findById(taskId)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+" with projectId"+taskId));
        return userRepository
                .getUserByTaskId(taskId)
                .stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllDidByTaskId(Integer taskId) {
        taskRepository.findById(taskId)
                .orElseThrow(()
                        -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()+" with projectId"+taskId));
        return userRepository
                .getUserDidDoingByTaskId(taskId)
                .stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Not Found"));

        return User.builder().username(user.getEmail()).password(user.getPassword()).roles().build();
    }


}
