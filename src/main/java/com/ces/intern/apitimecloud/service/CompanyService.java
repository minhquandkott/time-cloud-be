package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.CompanyDTO;
import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.CompanyEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;

import java.util.List;

public interface CompanyService {

    CompanyDTO getCompany(Integer companyId);

    CompanyDTO createCompany(CompanyDTO companyDTO, Integer userId);

    CompanyDTO updateCompany(Integer companyId, CompanyDTO companyDTO,Integer userId);

    void deleteCompany(Integer companyId);

}