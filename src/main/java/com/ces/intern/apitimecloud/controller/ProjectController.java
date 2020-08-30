package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.http.request.ProjectRequest;
import com.ces.intern.apitimecloud.http.response.ProjectResponse;
import com.ces.intern.apitimecloud.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {


    private final ProjectService projectService;
    private final ModelMapper modelMapper;


    public ProjectController(ProjectService projectService, ModelMapper modelMapper){

        this.projectService = projectService;

        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/test")
    public ResponseEntity<String> testSpringBoot() {
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/project/{id}")
    public ProjectResponse createProject(@RequestBody ProjectRequest request, @PathVariable Integer id){

        ProjectDTO project = modelMapper.map(request, ProjectDTO.class);

        ProjectDTO projectDTO = projectService.createProject(id,project);

        ProjectResponse response = modelMapper.map(projectDTO, ProjectResponse.class);

        return response;
    }

    @GetMapping("/project/{id}")
    public ProjectResponse getProject(@PathVariable Integer id){

        ProjectDTO projectDTO = projectService.getProject(id);

        ProjectResponse response = modelMapper.map(projectDTO,ProjectResponse.class);

        return response;
    }

    @GetMapping("/project")
    public List getAllProject(){

        List listProjects = projectService.getAllProject();

        listProjects.stream().map(t->modelMapper.map(t,ProjectResponse.class));

        return listProjects;
    }

    @PutMapping("/project/{id}")
    public ProjectResponse updateProject(@RequestBody ProjectRequest projectRequest, @PathVariable Integer id){

        ProjectDTO project = modelMapper.map(projectRequest,ProjectDTO.class);

        ProjectDTO projectDTO = projectService.updateProject(id,project);

        ProjectResponse projectResponse = modelMapper.map(projectDTO,ProjectResponse.class);

        return  projectResponse;
    }

    @DeleteMapping("/project")
    public void deleteProject(@RequestBody Integer[] ids){
        projectService.deleteProject(ids);
    }
}
