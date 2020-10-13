package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.dto.UserRoleDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserRoleService {


    UserRoleDTO addRoleUserInCompany(Integer userId, Integer companyId, Integer roleId);

    UserDTO addUserToCompany(Integer userId, Integer companyId);

    List<UserRoleDTO> getAllByCompanyIdAndRoleId(Integer companyId, Integer roleId);

    List<UserRoleDTO> getAllByCompanyIdAndUserId(Integer companyId, Integer userId);

    List<UserRoleDTO>  getAllByCompanyId(Integer companyId);

    void deleteUserRole(Integer userId, Integer companyId, Integer roleId);

}
