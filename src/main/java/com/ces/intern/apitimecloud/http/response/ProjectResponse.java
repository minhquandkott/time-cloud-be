package com.ces.intern.apitimecloud.http.response;
import com.ces.intern.apitimecloud.dto.CompanyDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ProjectResponse {

    private Integer id;
    private String name;
    private String clientName;
    private Date creatAt;
    private Integer createBy;
    private Date modifyAt;
    private Integer modifyBy;
    private CompanyDTO company;
}
