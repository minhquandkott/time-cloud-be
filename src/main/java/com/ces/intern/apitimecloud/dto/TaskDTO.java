package com.ces.intern.apitimecloud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TaskDTO {

    private Integer id;
    private String name;
    private Date createAt;
    private Date modifyAt;
    private Integer createBy;
    private Integer modifyBy;
    private ProjectDTO project;

}
