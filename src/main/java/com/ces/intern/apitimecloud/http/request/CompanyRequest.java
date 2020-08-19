package com.ces.intern.apitimecloud.http.request;

public class CompanyRequest {

    private Integer companyId;
    private Integer getCompanyName;

    public CompanyRequest() {
    }

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
}
