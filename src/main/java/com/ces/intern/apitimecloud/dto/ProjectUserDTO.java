package com.ces.intern.apitimecloud.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ProjectUserDTO {

    private ProjectDTO project;
    private UserDTO user;
    private Boolean isDoing;
}
