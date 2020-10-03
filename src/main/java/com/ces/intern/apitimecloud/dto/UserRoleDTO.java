package com.ces.intern.apitimecloud.dto;

import com.ces.intern.apitimecloud.entity.RoleEntity;
import com.ces.intern.apitimecloud.util.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserRoleDTO{

    private UserDTO user;
    private CompanyDTO company;
    private RoleEntity role;
    private Integer createdAt;
    private Date createBy;
}
