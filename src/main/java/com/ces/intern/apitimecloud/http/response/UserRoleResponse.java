package com.ces.intern.apitimecloud.http.response;

import com.ces.intern.apitimecloud.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserRoleResponse {
    private UserResponse user;
    private CompanyResponse company;
    private RoleEntity role;
    private Integer createdBy;
    private Date createAt;

}
