package com.ces.intern.apitimecloud.http.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.NamingConvention;

@Setter
@Getter
@NoArgsConstructor
public class CompanyResponse extends BaseResponse{

    private Integer id;
    private String name;
    private String avatar;
    private String description;
    private String logo;

}
