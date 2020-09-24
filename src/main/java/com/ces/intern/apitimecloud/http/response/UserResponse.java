package com.ces.intern.apitimecloud.http.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse extends BaseResponse{

    private Integer id;
    private String name;
    private String email;
    private boolean gender;
    private String address;
    private String phoneNumber;
    private String avatar;

}
