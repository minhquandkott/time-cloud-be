package com.ces.intern.apitimecloud.controller;


import com.ces.intern.apitimecloud.dto.CompanyDTO;
import com.ces.intern.apitimecloud.http.request.CompanyRequest;
import com.ces.intern.apitimecloud.http.response.CompanyResponse;
import com.ces.intern.apitimecloud.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CompanyController {
    final private CompanyService companyService;
    final private ModelMapper modelMapper;

    @Autowired
    public CompanyController(CompanyService companyService,
                             ModelMapper modelMapper){
        this.companyService = companyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public CompanyResponse getCompany(@PathVariable Integer id){

        CompanyResponse returnValue = new CompanyResponse();

        CompanyDTO company = companyService.getCompany(id);

        returnValue = modelMapper.map(company, CompanyResponse.class);

        return returnValue;
    }

    @PostMapping
    public String createCompany(){
        return "post";
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
