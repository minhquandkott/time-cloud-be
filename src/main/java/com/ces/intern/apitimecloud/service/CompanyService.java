package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.CompanyDTO;
import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.entity.CompanyEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;

import java.util.List;

public interface CompanyService {

    public CompanyDTO getCompany(Integer companyId);

    public CompanyDTO createCompany(CompanyDTO companyDTO);

    public CompanyDTO updateCompany(Integer companyId, CompanyDTO companyDTO);

    public void deleteCompany(Integer companyId);

    public List<ProjectDTO> getProjects(Integer companyId);

    public CompanyEntity getFullInFor(Integer companyId);

    public List<UserEntity> getMembers(Integer companyId);

    public List<UserEntity> getMemberByRole(Integer companyId, Integer role);
}
