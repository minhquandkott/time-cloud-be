package com.ces.intern.apitimecloud.http.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiscussionRequest {

    private Integer userId;
    private Integer projectId;
    private String content;
    private Integer type;
}
