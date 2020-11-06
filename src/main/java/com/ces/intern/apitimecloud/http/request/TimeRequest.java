package com.ces.intern.apitimecloud.http.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TimeRequest {

    private Long mileSecondStartTime;
    private Long mileSecondEndTime;
    private String description;
    private Integer taskId;
    private Integer userId;

}
