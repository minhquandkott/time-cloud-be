package com.ces.intern.apitimecloud.http.response;

import com.ces.intern.apitimecloud.dto.DiscussionDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentResponse extends BaseResponse{

    private String content;
    private DiscussionDTO discussion;
    private UserDTO user;
}

