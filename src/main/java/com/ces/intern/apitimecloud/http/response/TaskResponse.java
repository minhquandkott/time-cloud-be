package com.ces.intern.apitimecloud.http.response;

import com.ces.intern.apitimecloud.dto.ProjectDTO;

import java.util.Date;

public class TaskResponse {

    private String name;
    private Date createAt;
    private Date modifyAt;
    private ProjectDTO project;

    public TaskResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }
}
