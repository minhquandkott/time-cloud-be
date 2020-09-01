package com.ces.intern.apitimecloud.controller;


import com.ces.intern.apitimecloud.dto.CompanyDTO;
import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.ProjectEntity;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.CompanyRequest;
import com.ces.intern.apitimecloud.http.response.CompanyResponse;
import com.ces.intern.apitimecloud.http.response.ProjectResponse;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.security.config.SecurityContact;
import com.ces.intern.apitimecloud.service.CompanyService;
import com.ces.intern.apitimecloud.service.ProjectService;
import com.ces.intern.apitimecloud.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/company")
public class CompanyController {
    final private CompanyService companyService;
    final private ModelMapper modelMapper;
    final private UserService userService;
    final private ProjectService projectService;

    @Autowired
    public CompanyController(CompanyService companyService,
                             ModelMapper modelMapper,
                             UserService userService,
                             ProjectService projectService
                             ){
        this.companyService = companyService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping(value = "/{id}")
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

    @GetMapping(value = "/{id}/users")
    public List<UserResponse> getUsersByCompanyId(@PathVariable Integer id){

        List<UserDTO> users =  userService.getAllByCompanyId(id);

        return users.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());

    }

    @GetMapping(value = "/{id}/users/{roleId}")
    public List<UserResponse> getUsersByCompanyIdAndRole(@PathVariable Integer id, @PathVariable Integer roleId){

        List<UserDTO> users =  userService.getAllByCompanyAndRole(id, roleId);

        return users.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());

    }
    @GetMapping(value = "/{id}/projects")
    public List<ProjectResponse> getProject(@PathVariable Integer id){

        List<ProjectDTO> projects = projectService.getAllByCompanyId(id);
        return projects.stream()
                .map(project  -> modelMapper.map(project, ProjectResponse.class))
                .collect(Collectors.toList());
    }


}
