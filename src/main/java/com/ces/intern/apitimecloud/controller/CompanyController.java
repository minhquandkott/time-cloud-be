package com.ces.intern.apitimecloud.controller;


import com.ces.intern.apitimecloud.dto.CompanyDTO;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.CompanyRequest;
import com.ces.intern.apitimecloud.http.response.CompanyResponse;
import com.ces.intern.apitimecloud.security.config.SecurityContact;
import com.ces.intern.apitimecloud.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus
    public CompanyResponse getCompany(@PathVariable Integer id, @RequestHeader(SecurityContact.HEADER_STRING) String userId ) throws Exception  {

        CompanyResponse response = new CompanyResponse();

        CompanyDTO company = companyService.getCompany(id);

        response = modelMapper.map(company, CompanyResponse.class);

        return response;

    }

    @PostMapping
    public CompanyResponse createCompany(@RequestBody CompanyRequest request){

        if(request.getName() ==  null) throw new BadRequestException("Missing company name");

        CompanyResponse response = new CompanyResponse();

        CompanyDTO company = modelMapper.map(request, CompanyDTO.class);

        response = modelMapper.map(companyService.createCompany(company), CompanyResponse.class);

        return response;
    }

    @PutMapping(value = "/{id}")
    public CompanyResponse updateCompany(@PathVariable Integer id, @RequestBody CompanyRequest request){

        CompanyResponse response = new CompanyResponse();

        CompanyDTO company = modelMapper.map(request, CompanyDTO.class);
        response = modelMapper.map(companyService.updateCompany(id, company), CompanyResponse.class);

        return response;
    }

    @DeleteMapping(value = "/{id}")
    public String deleteCompany(@PathVariable Integer id){

        companyService.deleteCompany(id);
        return "OK";
    }
}
