package com.ces.intern.apitimecloud.http.request;

public class ProjectRequest {

    private String name;
    private String clientName;

    public ProjectRequest(){}

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
