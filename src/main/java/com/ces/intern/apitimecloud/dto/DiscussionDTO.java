package com.ces.intern.apitimecloud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiscussionDTO extends BaseDTO{

    private String content;
    private Integer type;
    private UserDTO user;
    private ProjectDTO project;
}
