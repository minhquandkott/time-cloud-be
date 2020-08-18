package com.ces.intern.apitimecloud.http.response;

public class CompanyResponse {

    private Integer companyId;
    private Integer getCompanyName;
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

    public Integer getGetCompanyName() {
        return getCompanyName;
    }

    public void setGetCompanyName(Integer getCompanyName) {
        this.getCompanyName = getCompanyName;
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
