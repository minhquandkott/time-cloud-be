package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.CompanyDTO;
import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.CompanyEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;

import java.util.List;

public interface CompanyService {

    public CompanyDTO getCompany(Integer companyId);

    public CompanyDTO createCompany(CompanyDTO companyDTO, Integer userId);

    public CompanyDTO updateCompany(Integer companyId, CompanyDTO companyDTO,Integer userId);

    public void deleteCompany(Integer companyId);

    public CompanyEntity getFullInFor(Integer companyId);

    public List<UserEntity> getMemberByRole(Integer companyId, String role);


}