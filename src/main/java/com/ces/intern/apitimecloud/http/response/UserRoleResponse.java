package com.ces.intern.apitimecloud.http.response;

import com.ces.intern.apitimecloud.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class UserRoleResponse extends BaseResponse {
    private UserResponse user;

    private CompanyResponse company;

    private RoleEntity role;

}
