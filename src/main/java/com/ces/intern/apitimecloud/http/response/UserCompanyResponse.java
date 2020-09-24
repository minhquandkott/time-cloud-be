package com.ces.intern.apitimecloud.http.response;

public class UserCompanyResponse {
    private UserResponse userResponse;

    private CompanyResponse companyResponse;

    private String role;

    public UserCompanyResponse(){

    }

    public UserCompanyResponse(UserResponse userResponse, CompanyResponse companyResponse, String role) {
        this.userResponse = userResponse;
        this.companyResponse = companyResponse;
        this.role = role;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    public CompanyResponse getCompanyResponse() {
        return companyResponse;
    }

    public void setCompanyResponse(CompanyResponse companyResponse) {
        this.companyResponse = companyResponse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
