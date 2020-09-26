package com.ces.intern.apitimecloud.service;

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
    public String save(UserRequest userRequest);

    public UserResponse findUser(Integer id);

    public UserResponse update(UserRequest userRequest, Integer id, Integer modifiedBy);

    public void delete(int[] ids);

    public UserDTO findByEmail(String email);

    public List<UserRoleDTO> getAllByCompanyId(Integer companyId);

    public List<UserDTO> getAllByCompanyAndRole(Integer companyId, Integer roleId);

    public UserDTO validateUser(String email, String password);
}
