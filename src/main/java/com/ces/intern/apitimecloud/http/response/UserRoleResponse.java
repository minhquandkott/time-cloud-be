package com.ces.intern.apitimecloud.http.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class UserRoleResponse extends BaseResponse {
    private UserResponse userResponse;

    private CompanyResponse companyResponse;

    private String role;

}
