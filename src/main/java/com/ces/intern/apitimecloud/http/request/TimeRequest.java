package com.ces.intern.apitimecloud.http.request;

import java.util.Date;

public class TimeRequest {
    private Long mileSecondStartTime;
    private Long mileSecondEndTime;
    private String description;

    public Long getMileSecondStartTime() {
        return mileSecondStartTime;
    }

    public void setMileSecondStartTime(Long mileSecondStartTime) {
        this.mileSecondStartTime = mileSecondStartTime;
    }

    public Long getMileSecondEndTime() {
        return mileSecondEndTime;
    }

    public void setMileSecondEndTime(Long mileSecondEndTime) {
        this.mileSecondEndTime = mileSecondEndTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
