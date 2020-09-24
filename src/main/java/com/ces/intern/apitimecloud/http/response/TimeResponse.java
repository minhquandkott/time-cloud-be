package com.ces.intern.apitimecloud.http.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TimeResponse extends BaseResponse{

    private Integer id;
    private Date startTime;
    private Date endTime;
    private String description;
    private UserResponse user;
    private TaskResponse task;

}
