package com.ces.intern.apitimecloud.http.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class BaseResponse {
    private Date createAt;
    private Integer createdBy;
    private Date modifyAt;
    private Integer modifiedBy;
}
