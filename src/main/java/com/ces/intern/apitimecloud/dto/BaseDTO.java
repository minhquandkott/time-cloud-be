package com.ces.intern.apitimecloud.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public abstract class BaseDTO {

    private Integer id;
    private Date createAt;
    private Integer createdBy;
    private Date modifyAt;
    private Integer modifiedBy;

}
