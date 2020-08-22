package com.ces.intern.apitimecloud.service.impl;


import com.ces.intern.apitimecloud.dto.CompanyDTO;
import com.ces.intern.apitimecloud.entity.CompanyEntity;
import com.ces.intern.apitimecloud.http.request.CompanyRequest;
import com.ces.intern.apitimecloud.repository.CompanyRepository;
import com.ces.intern.apitimecloud.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;


@Service
public class CompanyServiceImpl implements CompanyService {

    final private ModelMapper modelMapper;
    final private CompanyRepository companyRepository;

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

       CompanyEntity company_ = optional.orElseThrow(() -> new RuntimeException("Not found"));

       returnValue = modelMapper.map(company_, CompanyDTO.class);

       return returnValue;
    }

    @Override
    public CompanyDTO createCompany(CompanyDTO company) {

        CompanyDTO returnValue = new CompanyDTO();

        CompanyEntity _company = modelMapper.map(company, CompanyEntity.class);

        CompanyEntity company_ = companyRepository.save(_company);

        returnValue = modelMapper.map(company_, CompanyDTO.class);

        return returnValue;
    }

    @Override
    public CompanyDTO updateCompany(Integer companyId, CompanyDTO company) {

        CompanyDTO returnValue = new CompanyDTO();

        //CompanyEntity


        return returnValue;
    }

    @Override
    public void deleteCompany(Integer compayId) {

    }
}
