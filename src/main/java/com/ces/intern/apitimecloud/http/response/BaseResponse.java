package com.ces.intern.apitimecloud.http.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse {
    private Date createAt;
    private Integer createBy;
    private Date modifyAt;
    private Integer modifyBy;
}
