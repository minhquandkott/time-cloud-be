package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.*;
import com.ces.intern.apitimecloud.entity.TaskUserEntity;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.ProjectRequest;
import com.ces.intern.apitimecloud.http.request.TaskRequest;
import com.ces.intern.apitimecloud.http.response.ProjectResponse;
import com.ces.intern.apitimecloud.http.response.ProjectUserResponse;
import com.ces.intern.apitimecloud.http.response.TaskResponse;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import com.ces.intern.apitimecloud.service.*;
import com.ces.intern.apitimecloud.util.Classifications;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.ResponseMessage;
import com.ces.intern.apitimecloud.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
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
    private final DiscussionService discussionService;


    @Autowired
    public ProjectController(ProjectService projectService,
                             ModelMapper modelMapper,
                             TaskService taskService,
                             TimeService timeService,
                             UserService userService,
                             DiscussionService discussionService){
        this.projectService = projectService;
        this.modelMapper = modelMapper;
        this.taskService = taskService;
        this.timeService = timeService;
        this.userService = userService;
        this.discussionService = discussionService;
    }

    @GetMapping("/api/test")
    public ResponseEntity<String> testSpringBoot(){
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

    @GetMapping("{projectId}/users/{userId}/tasks-did")
    public List<TaskResponse> getAllTaskDidDoingByProjectIdAndUserId(@PathVariable Integer projectId, @PathVariable Integer userId){
        List<TaskDTO> taskDTOS= taskService.getAllDidDoingByUserIdAndProjectId(userId, projectId);
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
    public List<ProjectUserResponse> getAllUserByProjectId(@PathVariable("projectId") Integer projectId,
                                                           @RequestParam(value = "is_doing", required = false) Optional<Boolean> isDoing){
        if(projectId == null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "projectId");
        if(isDoing.isPresent()){
            return projectService.getAllUserByIsDoing(projectId, isDoing.get())
                    .stream()
                    .map(projectUser -> modelMapper.map(projectUser, ProjectUserResponse.class))
                    .collect(Collectors.toList());
        }
        return userService.getAllByProjectId(projectId)
                .stream()
                .map(projectUserDTO -> modelMapper.map(projectUserDTO, ProjectUserResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{projectId}/available")
    public boolean checkProjectAvailable(@PathVariable("projectId")Integer projectId){
        return projectService.checkProjectAvailable(projectId);
    }

    @GetMapping("/{projectId}/users/{userId}/total-times")
    public Float getSumTimeByUserProject(@PathVariable("userId") Integer userId,@PathVariable("projectId") Integer projectId){
        if(projectId==null||userId==null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "projectId" +"or"+"userId");
        return timeService.sumTimeByUserProject(userId,projectId);
    }

    @GetMapping("/{projectId}/date/{date}/total-times")
    public Float getSumTimeByDayOfProject(@PathVariable("projectId") Integer projectId,@PathVariable("date") String date) throws ParseException {
        if(projectId == null || date == null ) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "projectId" +" or "+"date");
        return timeService.sumTimeByDayOfProject(projectId,date, Utils.toNumbersOfDay(date,1));
    }

    @GetMapping("/{projectId}/date/{date}/all-week-times")
    public List<Float> getAllSumTimesByDayOfWeekOfProject(@PathVariable("projectId") Integer projectId,@PathVariable("date") String date) throws ParseException{
        if(projectId == null || date == null ) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "projectId" +" or "+"date");
        return timeService.getAllSumTimesByDayOfWeekOfProject(projectId,date);
    }

    @GetMapping(value = "/{projectId}/users/{userId}/task_user")
    public List<TaskUserEntity> add(@PathVariable("projectId") Integer projectId, @PathVariable("userId")Integer userId){

        return taskService.getAllTaskUsersByProjectIdAndUserId(projectId, userId);
    }

    @GetMapping(value = "/{projectId}/discussions")
    public List<DiscussionDTO> getAllDiscussionByProjectIdAndType(@PathVariable(value = "projectId")Integer projectId,
                                                           @RequestParam(value = "type", required = false)Integer type,
                                                           @RequestParam(value = "limit") Integer limit,
                                                           @RequestParam(value = "page") Integer page,
                                                           @RequestParam(value = "sort_by", required = false) String sortBy,
                                                           @RequestParam(value = "order", defaultValue = "ASC") String order){


        if(sortBy==null){
            if(type == null){
                return discussionService.getAllByProjectId(projectId, limit, page, "", order);
            }
            return  discussionService.getAllByProjectIdAndType(projectId, type, limit, page, "", order);
        }else{
            if(!Utils.containFiledName(DiscussionDTO.class, sortBy))
                throw  new BadRequestException(ExceptionMessage.FIELD_NOT_CORRECT.getMessage() + " sortBy " +sortBy);
            else if(type == null){
                return discussionService.getAllByProjectId(projectId, limit, page, sortBy, order);
            }
            return  discussionService.getAllByProjectIdAndType(projectId, type, limit, page, sortBy, order );
        }

    }

    @PutMapping("/{projectId}/users/{userId}/{newIndex}")
    public ProjectUserDTO changeIndexOfProjectUser(@PathVariable(value = "projectId")Integer projectId,
                                               @PathVariable(value = "userId")Integer userId,
                                                   @PathVariable(value = "newIndex")Integer newIndex){
        if(newIndex == null) throw  new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage());
        return projectService.changeIndexOfProjectUser(projectId, userId, newIndex);
    }

}
