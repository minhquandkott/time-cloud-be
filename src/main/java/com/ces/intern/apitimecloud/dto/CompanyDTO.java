package com.ces.intern.apitimecloud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDTO {

    private Integer id;
    private String name;
    private String avatar;
    private String description;
    private String logo;
    private Date createAt;
    private Integer createBy;
    private Date modifyAt;


}

