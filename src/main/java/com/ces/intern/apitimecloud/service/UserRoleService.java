package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.dto.UserRoleDTO;
import com.ces.intern.apitimecloud.entity.UserRoleEntity;
import com.ces.intern.apitimecloud.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserRoleService {


    public UserRoleDTO addRoleUserInCompany(Integer userId, Integer companyId, Integer roleId);

    public UserDTO addUserToCompany(Integer userId, Integer companyId, String role);
}
