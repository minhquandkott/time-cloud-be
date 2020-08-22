package com.ces.intern.apitimecloud.http.response;

import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.NamingConvention;

public class CompanyResponse {

    private Integer id;
    private String name;
    private String avatar;
    private String description;
    private String logo;

    public CompanyResponse(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
