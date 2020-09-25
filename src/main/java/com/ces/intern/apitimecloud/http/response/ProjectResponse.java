package com.ces.intern.apitimecloud.http.response;
import com.ces.intern.apitimecloud.dto.CompanyDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ProjectResponse extends BaseResponse{

    private Integer id;
    private String name;
    private String clientName;
    private CompanyDTO company;
}
