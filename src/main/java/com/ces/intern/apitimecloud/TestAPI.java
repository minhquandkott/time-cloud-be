package com.ces.intern.apitimecloud;

import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.http.request.ProjectRequest;
import com.ces.intern.apitimecloud.http.request.ProjectUpdateRequest;
import com.ces.intern.apitimecloud.http.response.ProjectResponse;
import com.ces.intern.apitimecloud.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestAPI {


    private final ProjectService projectService;
    private final ModelMapper modelMapper;


    public TestAPI(ProjectService projectService,
                   ModelMapper modelMapper){
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

    @GetMapping("project/{id}")
    public ProjectResponse getProject(@PathVariable Integer id){

        ProjectDTO projectDTO = projectService.getProject(id);

        ProjectResponse response = modelMapper.map(projectDTO,ProjectResponse.class);

        return response;
    }

    @GetMapping("/project/all")
    public List getAllProject(){
        List listProjects = projectService.getAllProject();
        listProjects.stream().map(t->modelMapper.map(t,ProjectResponse.class));
        return listProjects;
    }

    @PostMapping("/project/update/{id}")
    public ProjectResponse updateProject(@RequestBody ProjectUpdateRequest projectUpdateRequest, @PathVariable Integer id){
        ProjectDTO project = modelMapper.map(projectUpdateRequest,ProjectDTO.class);
        ProjectDTO projectDTO = projectService.updateProject(id,project);
        ProjectResponse projectResponse = modelMapper.map(projectDTO,ProjectResponse.class);
        return  projectResponse;
    }

    @DeleteMapping("/project")
    public void deleteProject(@RequestBody Integer[] ids){
        projectService.deleteProject(ids);
    }
}
