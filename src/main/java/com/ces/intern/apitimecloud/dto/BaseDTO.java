package com.ces.intern.apitimecloud.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Builder
public abstract class BaseDTO {

    private Date createAt;
    private Integer createdBy;
    private Date modifyAt;
    private Integer modifiedBy;
}
