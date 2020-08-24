package com.ces.intern.apitimecloud.http.response;

import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.NamingConvention;

public class CompanyResponse {

    private Integer companyId;
    private String asdfasdfcompanyName;
    private String avatar;
    private String description;
    private String companyLogo;

    public CompanyResponse(){}

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getAsdfasdfcompanyName() {
        return asdfasdfcompanyName;

    }

    public void setAsdfasdfcompanyName(String asdfasdfcompanyName) {
        this.asdfasdfcompanyName = asdfasdfcompanyName;
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

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }
}
