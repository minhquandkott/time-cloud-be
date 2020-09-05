package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.http.request.ProjectRequest;
import com.ces.intern.apitimecloud.http.request.TaskRequest;
import com.ces.intern.apitimecloud.http.response.ProjectResponse;
import com.ces.intern.apitimecloud.http.response.TaskResponse;
import com.ces.intern.apitimecloud.service.ProjectService;
import com.ces.intern.apitimecloud.service.TaskService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {


    private final ProjectService projectService;
    private final ModelMapper modelMapper;
    private final TaskService taskService;


    public ProjectController(ProjectService projectService,
                             ModelMapper modelMapper,
                             TaskService taskService){
        this.projectService = projectService;
        this.modelMapper = modelMapper;
        this.taskService = taskService;
    }

    @GetMapping("/api/test")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public ResponseEntity<String> testSpringBoot() {
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public ProjectResponse createProject(@RequestBody ProjectRequest request, @PathVariable Integer id,
                                         @RequestHeader("userId") String userId){

        ProjectDTO project = modelMapper.map(request, ProjectDTO.class);

        ProjectDTO projectDTO = projectService.createProject(id,project,userId);

        ProjectResponse response = modelMapper.map(projectDTO, ProjectResponse.class);

        return response;
    }

    @GetMapping("/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public ProjectResponse getProject(@PathVariable Integer id){

        ProjectDTO projectDTO = projectService.getProject(id);

        ProjectResponse response = modelMapper.map(projectDTO,ProjectResponse.class);

        return response;
    }

    @GetMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public List getAllProject(){

        List listProjects = projectService.getAllProject();

        listProjects.stream().map(t->modelMapper.map(t,ProjectResponse.class));

        return listProjects;
    }

    @PutMapping("/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public ProjectResponse updateProject(@RequestBody ProjectRequest projectRequest, @PathVariable Integer id){

        ProjectDTO project = modelMapper.map(projectRequest,ProjectDTO.class);

        ProjectDTO projectDTO = projectService.updateProject(id,project);

        ProjectResponse projectResponse = modelMapper.map(projectDTO,ProjectResponse.class);

        return  projectResponse;
    }

    @DeleteMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public void deleteProject(@RequestBody Integer[] ids){
        projectService.deleteProject(ids);
    }

    @PostMapping("/{id}/tasks")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public TaskResponse createTask(@RequestBody TaskRequest request, @PathVariable Integer id,
                                   @RequestHeader("userId") String userId){

        TaskDTO task = modelMapper.map(request,TaskDTO.class);

        TaskDTO taskDTO = taskService.createTask(id,task,userId);

        TaskResponse response = modelMapper.map(taskDTO, TaskResponse.class);

        return response;
    }

    @GetMapping("/{id}/tasks")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public List<TaskResponse> getAllTaskByProjectId(@PathVariable Integer id){
        List<TaskDTO> list = taskService.getAllTaskByProject(id);
        return list.stream().map(task->modelMapper.map(task,TaskResponse.class)).collect(Collectors.toList());
    }
}
