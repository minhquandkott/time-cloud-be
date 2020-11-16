package com.ces.intern.apitimecloud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InteractDTO {
    private UserDTO user;
    private DiscussionDTO discussion;
}
