package com.ces.intern.apitimecloud.http.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ProjectUserResponse {

    private ProjectResponse project;
    private UserResponse user;
    private Boolean isDoing;
    private Integer index;
}
