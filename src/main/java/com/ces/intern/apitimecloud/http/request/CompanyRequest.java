package com.ces.intern.apitimecloud.http.request;

public class CompanyRequest {

    private String name;
    private String avatar;
    private String logo;
    private String description;

    public CompanyRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CompanyRequest{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", logo='" + logo + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
