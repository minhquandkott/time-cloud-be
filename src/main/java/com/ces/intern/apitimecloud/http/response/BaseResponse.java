package com.ces.intern.apitimecloud.http.response;

import lombok.*;

import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class BaseResponse {
    private Date createAt;
    private Integer createdBy;
    private Date modifyAt;
    private Integer modifiedBy;

    public void setBasicInfo(Date createAt, Integer createdBy, Date modifyAt, Integer modifiedBy){
        this.createAt = createAt;
        this.createdBy = createdBy;
        this.modifyAt = modifyAt;
        this.modifiedBy = modifiedBy;
    }
}
