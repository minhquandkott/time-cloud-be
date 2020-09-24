package com.ces.intern.apitimecloud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TimeDTO {

    private Integer id;
    private Date startTime;
    private Date endTime;
    private String description;
    private UserDTO user;
    private TaskDTO task;

}
