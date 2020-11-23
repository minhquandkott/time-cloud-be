package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.ProjectUserDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.dto.UserRoleDTO;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.request.UserRequest;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    String save(UserRequest userRequest);

    UserResponse findUser(Integer id);

    UserResponse update(UserRequest userRequest, Integer id, Integer modifiedBy);

    void delete(Integer userId);

    UserDTO findByEmail(String email);

    UserDTO validateUser(String email, String password);

    List<ProjectUserDTO> getAllByProjectId(Integer projectId);

    List<UserDTO> getAllByTaskId(Integer taskId);

    List<UserDTO> getAllDidByTaskId(Integer taskId);
}
