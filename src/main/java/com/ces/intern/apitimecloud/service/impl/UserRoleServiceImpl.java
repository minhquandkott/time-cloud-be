package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.UserRoleDTO;
import com.ces.intern.apitimecloud.repository.UserRoleRepository;
import com.ces.intern.apitimecloud.service.UserRoleService;

import java.util.List;

public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository){
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserRoleDTO> getAllUserByCompanyId(Integer companyId){

        return null;
    }
}
