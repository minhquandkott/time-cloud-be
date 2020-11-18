package com.ces.intern.apitimecloud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ProjectDTO extends BaseDTO {

    private String name;
    private String clientName;
    private CompanyDTO company;
    private String color;
    private Boolean done;
}
