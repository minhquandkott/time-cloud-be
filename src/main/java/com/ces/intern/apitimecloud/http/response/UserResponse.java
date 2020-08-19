package com.ces.intern.apitimecloud.http.response;

public class UserResponse {
    private String password;
    private String name;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }
}
