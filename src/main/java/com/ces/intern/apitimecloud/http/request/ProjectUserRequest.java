package com.ces.intern.apitimecloud.http.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectUserRequest {
    private Boolean isDoing;
    private Integer index;
    private Boolean isShow;
}
