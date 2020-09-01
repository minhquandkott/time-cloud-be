package com.ces.intern.apitimecloud.service.impl;


import com.ces.intern.apitimecloud.dto.CompanyDTO;
import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.entity.CompanyEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.repository.CompanyRepository;
import com.ces.intern.apitimecloud.service.CompanyService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;



@Service
public class CompanyServiceImpl implements CompanyService {

    final private ModelMapper modelMapper;
    final private CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(ModelMapper modelMapper,
                              CompanyRepository companyRepository){
        this.modelMapper = modelMapper;
        this.companyRepository = companyRepository;
    }


    // _company is entity before save in db
    // company_ is entity get from db


    @Override
    public CompanyDTO getCompany(Integer companyId) {
       CompanyDTO returnValue = new CompanyDTO();

       Optional<CompanyEntity> optional = companyRepository.findById(companyId);

       CompanyEntity company_ = optional.orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with " +companyId ) );

       returnValue = modelMapper.map(company_, CompanyDTO.class);

       return returnValue;
    }

    @Override
    public CompanyDTO createCompany(CompanyDTO company) {

        Date createAt = new Date();

        company.setCreateAt(createAt);
        company.setModifyAt(createAt);
        company.setCreateBy(20);

        CompanyDTO returnValue = new CompanyDTO();

        CompanyEntity _company = modelMapper.map(company, CompanyEntity.class);

        CompanyEntity company_ = companyRepository.save(_company);

        returnValue = modelMapper.map(company_, CompanyDTO.class);

        return returnValue;
    }

    @Override
    public CompanyDTO updateCompany(Integer companyId, CompanyDTO company) {

        CompanyDTO returnValue = new CompanyDTO();

        CompanyEntity company_ = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with" + companyId));
        Date modifyAt = new Date();

        TypeMap<CompanyDTO, CompanyEntity> tm = modelMapper
                .typeMap(CompanyDTO.class, CompanyEntity.class);
        tm.setPropertyCondition(Conditions.isNotNull());
        tm.map(company, company_);
        company_.setModifyAt(modifyAt);

        companyRepository.save(company_);
        returnValue = modelMapper.map(company_, CompanyDTO.class);

        return returnValue;
    }

    @Override
    public void deleteCompany(Integer companyId) {
        CompanyEntity company_ = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with" + companyId));
        companyRepository.delete(company_);
    }

    @Override
    public List<ProjectDTO> getProjects(Integer companyId) {
        return null;
    }

    @Override
    public CompanyEntity getFullInFor(Integer companyId) {
        return null;
    }

    @Override
    public List<UserEntity> getMembers(Integer companyId) {

        return null;
    }

    @Override
    public List<UserEntity> getMemberByRole(Integer companyId, Integer role) {
        return null;
    }
}
