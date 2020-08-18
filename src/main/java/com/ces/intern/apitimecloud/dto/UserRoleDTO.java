package com.ces.intern.apitimecloud.dto;

import com.ces.intern.apitimecloud.util.Role;

public class UserRoleDTO {
    private UserDTO user;
    private CompanyDTO company;
    private Role role;

    public UserRoleDTO(){}

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
