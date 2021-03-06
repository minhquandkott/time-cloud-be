package com.ces.intern.apitimecloud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends BaseDTO {

    private String name;
    private String email;
    private String password;
    private boolean gender;
    private String address;
    private String phoneNumber;
    private String avatar;
    private boolean isActive;
}
