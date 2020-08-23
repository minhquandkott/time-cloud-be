package com.ces.intern.apitimecloud.service.impl;


import com.ces.intern.apitimecloud.dto.CompanyDTO;
import com.ces.intern.apitimecloud.entity.CompanyEntity;
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


    @Override
    public CompanyDTO getCompany(Integer companyId) {
       CompanyDTO returnValue = new CompanyDTO();

       Optional<CompanyEntity> optional = companyRepository.findById(companyId);

       CompanyEntity company = optional.orElseThrow(() -> new RuntimeException("Not found"));

        System.out.println(company.getName());

       returnValue = modelMapper.map(company, CompanyDTO.class);

       return returnValue;
    }

    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        return null;
    }

    @Override
    public CompanyDTO updateCompany(Integer companyId, CompanyDTO companyDTO) {
        return null;
    }

    @Override
    public void deleteCompany(Integer compayId) {

    }
}
