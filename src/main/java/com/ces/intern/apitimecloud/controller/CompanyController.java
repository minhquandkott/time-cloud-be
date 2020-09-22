package com.ces.intern.apitimecloud.controller;


import com.ces.intern.apitimecloud.dto.CompanyDTO;
import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.ProjectEntity;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.CompanyRequest;
import com.ces.intern.apitimecloud.http.request.ProjectRequest;
import com.ces.intern.apitimecloud.http.response.CompanyResponse;
import com.ces.intern.apitimecloud.http.response.ProjectResponse;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.security.config.SecurityContact;
import com.ces.intern.apitimecloud.service.CompanyService;
import com.ces.intern.apitimecloud.service.ProjectService;
import com.ces.intern.apitimecloud.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companys")
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
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public CompanyResponse getCompany(@PathVariable Integer id, @RequestHeader(SecurityContact.HEADER_STRING) String userId ) throws Exception  {

        CompanyDTO company = companyService.getCompany(id);

        return modelMapper.map(company, CompanyResponse.class);

    }

    @PostMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public CompanyResponse createCompany(@RequestBody CompanyRequest request,
                                         @RequestHeader("userId") Integer userId){

        if(request.getName() ==  null || userId == null ) throw new BadRequestException("Missing require field (Company name or UserID - header)");

        CompanyDTO company = modelMapper.map(request, CompanyDTO.class);

        return modelMapper.map(companyService.createCompany(company, userId), CompanyResponse.class);
    }

    @PutMapping(value = "/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public CompanyResponse updateCompany(@PathVariable Integer id, @RequestBody CompanyRequest request){

        CompanyDTO company = modelMapper.map(request, CompanyDTO.class);

        return modelMapper.map(companyService.updateCompany(id, company), CompanyResponse.class);
    }

    @DeleteMapping(value = "/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public String deleteCompany(@PathVariable Integer id){

        companyService.deleteCompany(id);
        return "OK";
    }

    @GetMapping(value = "/{id}/users")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public List<UserResponse> getUsersByCompanyId(@PathVariable Integer id){

        List<UserDTO> users =  userService.getAllByCompanyId(id);

        return users.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());

    }

    @GetMapping(value = "/{companyId}/users/{role}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public List<UserResponse> getUsersByCompanyIdAndRole(@PathVariable(value = "companyId") Integer companyId, @PathVariable String role){

        List<UserDTO> users =  userService.getAllByCompanyAndRole(companyId, role);

        return users.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());

    }
    @GetMapping(value = "/{id}/projects")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public List<ProjectResponse> getProjects(@PathVariable Integer id){

        List<ProjectDTO> projects = projectService.getAllByCompanyId(id);
        return projects.stream()
                .map(project  -> modelMapper.map(project, ProjectResponse.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/projects")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public ProjectResponse createProject(@RequestBody ProjectRequest request, @PathVariable Integer id,
                                         @RequestHeader("userId") String userId){

        ProjectDTO project = modelMapper.map(request, ProjectDTO.class);

        ProjectDTO projectDTO = projectService.createProject(id,project,userId);
        return modelMapper.map(projectDTO, ProjectResponse.class);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    @PostMapping("/{companyId}/users/{userId}/add")
    public UserResponse addUserToCompany(@PathVariable Integer companyId, @PathVariable Integer userId, @RequestBody String role ){

        return modelMapper.map(companyService.addUserToCompany(userId, companyId, role), UserResponse.class);
    }
}
