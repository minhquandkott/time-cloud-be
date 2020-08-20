package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.CompanyDTO;

public interface CompanyService {

    public CompanyDTO getCompany(Integer companyId);

    public CompanyDTO createCompany(CompanyDTO companyDTO);

    public CompanyDTO updateCompany(Integer companyId, CompanyDTO companyDTO);

    public void deleteCompany(Integer compayId);
}
