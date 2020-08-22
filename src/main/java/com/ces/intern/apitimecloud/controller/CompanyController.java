package com.ces.intern.apitimecloud.controller;


import com.ces.intern.apitimecloud.dto.CompanyDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.http.request.CompanyRequest;
import com.ces.intern.apitimecloud.http.response.CompanyResponse;
import com.ces.intern.apitimecloud.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/company")
public class CompanyController {
    final private CompanyService companyService;
    final private ModelMapper modelMapper;



    @Autowired
    public CompanyController(CompanyService companyService,
                             ModelMapper modelMapper
                             ){
        this.companyService = companyService;
        this.modelMapper = modelMapper;

    }

    @GetMapping(value = "/{id}")
    public CompanyResponse getCompany(@PathVariable Integer id){

        CompanyResponse response = new CompanyResponse();

        CompanyDTO company = companyService.getCompany(id);

        response = modelMapper.map(company, CompanyResponse.class);

        return response;

    }

    @PostMapping
    public CompanyResponse createCompany(@RequestBody CompanyRequest request){
        CompanyResponse response = new CompanyResponse();

        CompanyDTO company = modelMapper.map(request, CompanyDTO.class);

        Date createTime = new Date();

        company.setCreateAt(createTime);
        company.setModifyAt(createTime);
        company.setCreateBy(20);

        response = modelMapper.map(companyService.createCompany(company), CompanyResponse.class);

        return response;
    }

    @PutMapping
    public String updateCompany(){
        return "update";
    }

    @DeleteMapping
    public String deleteCompany(){
        return "delete";
    }
}
