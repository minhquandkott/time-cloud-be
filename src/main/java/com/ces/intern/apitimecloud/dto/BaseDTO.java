package com.ces.intern.apitimecloud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public abstract class BaseDTO {

    private Date createAt;
    private Integer createBy;
    private Date modifyAt;
    private Integer modifyBy;
}
