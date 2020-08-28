package com.ces.intern.apitimecloud.http.request;

public class ProjectUpdateRequest {

    private String name;
    private String clientName;

    public ProjectUpdateRequest(){}

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
