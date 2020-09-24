package com.ces.intern.apitimecloud.http.response;

import com.ces.intern.apitimecloud.dto.ProjectDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TaskResponse {

    private Integer id;
    private String name;
    private Date createAt;
    private Date modifyAt;
    private Integer createBy;
    private Integer modifyBy;
    private ProjectDTO project;

}
