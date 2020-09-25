package com.ces.intern.apitimecloud.http.response;

import com.ces.intern.apitimecloud.dto.ProjectDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TaskResponse extends BaseResponse{

    private Integer id;
    private String name;
    private ProjectDTO project;

}
