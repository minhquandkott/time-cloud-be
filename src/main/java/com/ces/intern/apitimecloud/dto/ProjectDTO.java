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
public class ProjectDTO {

    private Integer id;
    private String name;
    private String clientName;
    private Date creatAt;
    private Integer createBy;
    private Date modifyAt;
    private Integer modifyBy;
    private CompanyDTO company;
}
