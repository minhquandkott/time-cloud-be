package com.ces.intern.apitimecloud.controller;


import com.ces.intern.apitimecloud.dto.CompanyDTO;
import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.dto.UserRoleDTO;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.CompanyRequest;
import com.ces.intern.apitimecloud.http.request.ProjectRequest;
import com.ces.intern.apitimecloud.http.response.CompanyResponse;
import com.ces.intern.apitimecloud.http.response.ProjectResponse;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.http.response.UserRoleResponse;
import com.ces.intern.apitimecloud.service.CompanyService;
import com.ces.intern.apitimecloud.service.ProjectService;
import com.ces.intern.apitimecloud.service.UserRoleService;
import com.ces.intern.apitimecloud.service.UserService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.ResponseMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
//@ApiImplicitParams({@ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")}) use for each method
public class CompanyController {
    private final CompanyService companyService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ProjectService projectService;
    private final UserRoleService userRoleService;


    @Autowired
    public CompanyController(CompanyService companyService,
                             ModelMapper modelMapper,
                             UserService userService,
                             ProjectService projectService,
                             UserRoleService userRoleService
                             ){
        this.companyService = companyService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.projectService = projectService;
        this.userRoleService = userRoleService;
    }

    @GetMapping(value = "/{id}")
    public CompanyResponse getCompany(@PathVariable Integer id) throws Exception  {

        CompanyDTO company = companyService.getCompany(id);

        return modelMapper.map(company, CompanyResponse.class);

    }

    @PostMapping
    public CompanyResponse createCompany(@RequestBody CompanyRequest request,
                                         @RequestHeader("userId") Integer userId){

        if(request.getName() ==  null || userId == null ) throw new BadRequestException("Missing require field (Company name or UserID - header)");

        CompanyDTO company = modelMapper.map(request, CompanyDTO.class);

        return modelMapper.map(companyService.createCompany(company, userId), CompanyResponse.class);
    }

    @PutMapping(value = "/{id}")
    public CompanyResponse updateCompany(@PathVariable Integer id, @RequestBody CompanyRequest request, @RequestHeader("userId") Integer userId){

        CompanyDTO company = modelMapper.map(request, CompanyDTO.class);

        return modelMapper.map(companyService.updateCompany(id, company, userId), CompanyResponse.class);
    }

    @DeleteMapping(value = "/{id}")
    public String deleteCompany(@PathVariable Integer id){

        companyService.deleteCompany(id);
        return "OK";
    }

    @GetMapping(value = "/{id}/users")
    public List<UserRoleResponse> getUsersByCompanyId(@PathVariable Integer id){

        List<UserRoleDTO> users = userRoleService.getAllByCompanyId(id);

        return users.stream()
                .map(user -> modelMapper.map(user, UserRoleResponse.class))
                .collect(Collectors.toList());

    }

    @GetMapping(value = "/{companyId}/role/{roleId}/users")
    public List<UserRoleResponse> getUsersByCompanyIdAndRoleId(@PathVariable(value = "companyId") Integer companyId, @PathVariable Integer roleId){

        List<UserRoleDTO> users =  userRoleService.getAllByCompanyIdAndRoleId(companyId, roleId);

        return users.stream()
                .map(user -> modelMapper.map(user, UserRoleResponse.class))
                .collect(Collectors.toList());

    }

    @GetMapping(value = "/{companyId}/users/{userId}/role")
    public List<UserRoleResponse> getUsersByCompanyIdAndUserId(@PathVariable(value = "companyId") Integer companyId, @PathVariable Integer userId){

        List<UserRoleDTO> users =  userRoleService.getAllByCompanyIdAndUserId(companyId, userId);

        return users.stream()
                .map(user -> modelMapper.map(user, UserRoleResponse.class))
                .collect(Collectors.toList());

    }

    @GetMapping(value = "/{id}/projects")
    public List<ProjectResponse> getProjects(@PathVariable Integer id){

        List<ProjectDTO> projects = projectService.getAllByCompanyId(id);
        return projects.stream()
                .map(project  -> modelMapper.map(project, ProjectResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}/projects-available")
    public List<ProjectResponse> getAllStillDoingByCompanyId(@PathVariable("id") Integer companyId){
        if(companyId == null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "id");
        List<ProjectDTO> listProjects = projectService.getAllStillDoingByCompanyId(companyId);
        return listProjects.stream().map(project->modelMapper.map(project,ProjectResponse.class)).collect(Collectors.toList());
    }

    @PostMapping("/{id}/projects")
    public ProjectResponse createProject(@RequestBody ProjectRequest request, @PathVariable Integer id,
                                         @RequestHeader("userId") Integer userId){


        ProjectDTO projectDTO = projectService.createProject(id ,request,userId);
        return modelMapper.map(projectDTO, ProjectResponse.class);
    }


    @PostMapping("/{companyId}/users/{userId}")
    public UserResponse addUserToCompany(@PathVariable Integer companyId, @PathVariable Integer userId ){
        return modelMapper.map(userRoleService.addUserToCompany(userId, companyId), UserResponse.class);
    }

    @PostMapping("/{companyId}/role/{roleId}/users/{userId}")
    public UserRoleResponse addUserToCompanyWithRole(@PathVariable Integer companyId, @PathVariable Integer userId, @PathVariable Integer roleId ){
        return modelMapper.map(userRoleService.addRoleUserInCompany(userId, companyId, roleId), UserRoleResponse.class);
    }

    @DeleteMapping("/{companyId}/role/{roleId}/users/{userId}")
    public String deleteUserRole(@PathVariable Integer companyId, @PathVariable Integer userId, @PathVariable Integer roleId ){
        userRoleService.deleteUserRole(userId, companyId, roleId);
        return ResponseMessage.DELETE_SUCCESS;
    }

}
