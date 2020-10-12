package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.ProjectDTO;
import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.dto.TimeDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.UserRequest;
import com.ces.intern.apitimecloud.http.response.ProjectResponse;
import com.ces.intern.apitimecloud.http.response.TaskResponse;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.repository.TimeRepository;
import com.ces.intern.apitimecloud.service.ProjectService;
import com.ces.intern.apitimecloud.service.TaskService;
import com.ces.intern.apitimecloud.service.TimeService;
import com.ces.intern.apitimecloud.service.UserService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.ResponseMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
//@ApiImplicitParams({@ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")}) use for each method
public class UserController {

    private final UserService userService;
    private final ProjectService projectService;
    private final ModelMapper modelMapper;
    private final TimeService timeService;
    private final TaskService taskService;

    @Autowired
    public UserController(UserService userService,
                          ProjectService projectService,
                          ModelMapper modelMapper,
                          TimeService timeService,
                          TaskService taskService){
        this.userService = userService;
        this.projectService = projectService;
        this.modelMapper = modelMapper;
        this.timeService = timeService;
        this.taskService = taskService;
    }

    @PostMapping(value ="")
    public String createUser(@RequestBody UserRequest userRequest)
    {
        return userService.save(userRequest);
    }

    @GetMapping("/{id}")

    public UserResponse findUser(@PathVariable Integer id)
    {
        return userService.findUser(id);
    }

    @PutMapping("/{id}")

    public UserResponse updateUser(@RequestBody UserRequest userRequest, @PathVariable Integer id, @RequestHeader Integer modifiedBy)
    {
        return userService.update(userRequest, id, modifiedBy);
    }

    @DeleteMapping(value = "/{userId}")

    public String deleteUser(@PathVariable Integer userId)
    {
        userService.delete(userId);
        return ResponseMessage.DELETE_SUCCESS;
    }

    @GetMapping("/{id}/projects")
    public List<ProjectResponse> getProjectsByUserId(@PathVariable("id") Integer userId){
        List<ProjectDTO> projects = projectService.getAllByUserId(userId);
        return projects.stream()
                .map(project  -> modelMapper.map(project, ProjectResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/projects/task-count")
    public List<ProjectResponse> getProjectsByUserIdOrderByTaskCount(@PathVariable("id") Integer userId){
        List<ProjectDTO> projects = projectService.getAllByUserIdOOrderByTaskCount(userId);
        return projects.stream()
                .map(project  -> modelMapper.map(project, ProjectResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}/times")
    public List<TimeResponse> getTimesByUserId(@PathVariable("id") Integer userId){
        List<TimeDTO> times = timeService.getTimesByUserId(userId);

        return times.stream()
                .map(time  -> modelMapper.map(time, TimeResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}/total-times")
    public Float getSumTimeByUserId(@PathVariable("userId") Integer userId){
        if(userId == null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "userID");
        return timeService.sumTimeByUserId(userId);
    }

    @GetMapping("{id}/tasks")
    public List<TaskResponse> getAllTasksByUserId(@PathVariable("id") Integer userId){
        List<TaskDTO> tasks = taskService.getAllTaskByUser(userId);
        return tasks.stream().map(task->modelMapper.map(task,TaskResponse.class)).collect(Collectors.toList());
    }
}
