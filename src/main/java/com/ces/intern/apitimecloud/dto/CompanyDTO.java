package com.ces.intern.apitimecloud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDTO extends BaseDTO {

    private String name;
    private String avatar;
    private String description;
    private String logo;

}

