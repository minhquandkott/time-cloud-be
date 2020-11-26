package com.ces.intern.apitimecloud.http.response;

import com.ces.intern.apitimecloud.dto.CompanyDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectResponse extends BaseResponse{

    private Integer id;
    private String name;
    private String clientName;
    private CompanyDTO company;
    private String color;
    private Boolean done;
    private UserDTO projectManager;
}
