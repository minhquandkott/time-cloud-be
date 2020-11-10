package com.ces.intern.apitimecloud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO extends BaseDTO{

    private String content;
    private DiscussionDTO discussion;
    private UserDTO user;
}
