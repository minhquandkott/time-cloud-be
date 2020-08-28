package com.ces.intern.apitimecloud.http.request;

public class ProjectRequest {

    private String name;
    private Integer createBy;

    public ProjectRequest(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }
}
