package com.ces.intern.apitimecloud.http.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private boolean gender;
    private String address;
    private String phoneNumber;
    private String avatar;
}
