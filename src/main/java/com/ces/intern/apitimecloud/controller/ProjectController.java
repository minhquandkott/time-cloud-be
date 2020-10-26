package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.dto.ProjectUserDTO;
import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.ProjectRequest;
import com.ces.intern.apitimecloud.http.request.TaskRequest;
import com.ces.intern.apitimecloud.http.response.ProjectResponse;
import com.ces.intern.apitimecloud.http.response.ProjectUserResponse;
import com.ces.intern.apitimecloud.http.response.TaskResponse;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.service.ProjectService;
import com.ces.intern.apitimecloud.service.TaskService;
import com.ces.intern.apitimecloud.service.TimeService;
import com.ces.intern.apitimecloud.service.UserService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.ResponseMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/projects")
//@ApiImplicitParams({@ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")}) use for each method
public class ProjectController {


    private final ProjectService projectService;
    private final ModelMapper modelMapper;
    private final TaskService taskService;
    private final TimeService timeService;
    private final UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService,
                             ModelMapper modelMapper,
                             TaskService taskService,
                             TimeService timeService,
                             UserService userService){
        this.projectService = projectService;
        this.modelMapper = modelMapper;
        this.taskService = taskService;
        this.timeService = timeService;
        this.userService = userService;
    }

    @GetMapping("/api/test")
    public ResponseEntity<String> testSpringBoot() {
        return ResponseEntity.ok("Success");
    }


    @GetMapping("/{id}")
    public ProjectResponse getProject(@PathVariable Integer id){

        ProjectDTO projectDTO = projectService.getProject(id);

        return modelMapper.map(projectDTO,ProjectResponse.class);
    }


    @GetMapping("")
    public List getAllProject(){

        Stream<ProjectResponse> projects = projectService
                .getAllProject()
                .stream().map(project -> modelMapper.map(project,ProjectResponse.class));

        return projects.collect(Collectors.toList());
    }


    @PutMapping("/{id}")
    public ProjectResponse updateProject(@RequestBody ProjectRequest projectRequest, @PathVariable Integer id
            ,@RequestHeader("userId") Integer userId){

        ProjectDTO project = modelMapper.map(projectRequest,ProjectDTO.class);

        ProjectDTO projectDTO = projectService.updateProject(id,project,userId);

        return  modelMapper.map(projectDTO,ProjectResponse.class);
    }


    @DeleteMapping("/{projectId}")
    public String deleteProject(@PathVariable Integer projectId){
        projectService.deleteProject(projectId);
        return ResponseMessage.DELETE_SUCCESS;
    }


    @PostMapping("/{id}/tasks")
    public TaskResponse createTask(@RequestBody TaskRequest request, @PathVariable Integer id,
                                   @RequestHeader("userId") Integer userId){

        TaskDTO task = modelMapper.map(request,TaskDTO.class);

        TaskDTO taskDTO = taskService.createTask(id,task,userId);

        return modelMapper.map(taskDTO, TaskResponse.class);
    }


    @GetMapping("/{id}/tasks")
    public List<TaskResponse> getAllTaskByProjectId(@PathVariable Integer id){
        List<TaskDTO> list = taskService.getAllTaskByProject(id);
        return list.stream().map(task->modelMapper.map(task,TaskResponse.class)).collect(Collectors.toList());
    }

    @PostMapping("{projectId}/users/{userId}")
    public ProjectUserResponse addUserToProject(@PathVariable Integer projectId, @PathVariable Integer userId){
        ProjectUserDTO projectUserDTO = projectService.addUserToProject(userId, projectId);
        return modelMapper.map(projectUserDTO,ProjectUserResponse.class);
    }

    @GetMapping("{projectId}/users/{userId}/tasks")
    public List<TaskResponse> getAllTaskByProjectIdAndUserId(@PathVariable Integer projectId, @PathVariable Integer userId){
        List<TaskDTO> taskDTOS= taskService.getAllByUserIdAndProjectId(userId, projectId);
        return taskDTOS.stream().map(task->modelMapper.map(task,TaskResponse.class)).collect(Collectors.toList());
    }

    @DeleteMapping("{projectId}/users/{userId}")
    public String deleteUserOfProject(@PathVariable Integer projectId, @PathVariable Integer userId){
        projectService.deleteUserOfProject(projectId, userId);
        return ResponseMessage.DELETE_SUCCESS;
    }

    @GetMapping("/{projectId}/total-times")
    public Float getSumTimeByProjectId(@PathVariable("projectId") Integer projectId){
        if(projectId == null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "projectId");
        return timeService.sumTimeByProjectId(projectId);
    }

    @GetMapping("/{projectId}/users")
    public List<ProjectUserResponse> getAllUserByProjectId(@PathVariable("projectId") Integer projectId){
        if(projectId == null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "projectId");
        return userService.getAllByProjectId(projectId)
                .stream()
                .map(projectUserDTO -> modelMapper.map(projectUserDTO, ProjectUserResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{projectId}/users/{userId}/total-times")
    public Float getSumTimeByUserProject(@PathVariable("userId") Integer userId,@PathVariable("projectId") Integer projectId){
        if(projectId==null||userId==null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "projectId" +"or"+"userId");
        return timeService.sumTimeByUserProject(userId,projectId);
    }

}
