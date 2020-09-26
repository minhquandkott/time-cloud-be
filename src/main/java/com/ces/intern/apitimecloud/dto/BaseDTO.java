package com.ces.intern.apitimecloud.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public abstract class BaseDTO {

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
