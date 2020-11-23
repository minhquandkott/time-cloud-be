package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.TaskRequest;
import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TaskResponse;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import com.ces.intern.apitimecloud.http.response.TimeSumResponse;
import com.ces.intern.apitimecloud.http.response.UserResponse;
import com.ces.intern.apitimecloud.service.TaskService;
import com.ces.intern.apitimecloud.service.TimeService;
import com.ces.intern.apitimecloud.service.UserService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.ResponseMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
//@ApiImplicitParams({@ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")}) use for each method
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;
    private final TimeService timeService;
    private final UserService userService;
    private final String DEFAULT_DESCRIPTION = "DEVELOPMENT";

    @Autowired
    public TaskController(TaskService taskService,
                          ModelMapper modelMapper,
                          TimeService timeService,
                          UserService userService){
        this.taskService = taskService;
        this.modelMapper = modelMapper;
        this.timeService = timeService;
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable Integer id){

        TaskDTO taskDTO = taskService.getTask(id);

        return modelMapper.map(taskDTO, TaskResponse.class);
    }


    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Integer id, @RequestBody TaskRequest request
                                    ,@RequestHeader("userId") Integer userId){

        TaskDTO taskDTO = modelMapper.map(request,TaskDTO.class);

        taskDTO = taskService.updateTask(id,taskDTO, userId);

        return modelMapper.map(taskDTO,TaskResponse.class);
    }


    @PostMapping(value = "/{id}/times")
    public TimeResponse createTime(@RequestHeader("userId") Integer userId,
                                   @RequestBody(required = true) TimeRequest timeRequest,
                                    @PathVariable("id") Integer taskId) {
        if(timeRequest.getDescription().isEmpty()) {
            timeRequest.setDescription(DEFAULT_DESCRIPTION);
        };
        return timeService.save(userId, timeRequest, taskId);
    }


    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Integer taskId){
        taskService.deleteTask(taskId);
    }

    @PostMapping("/{taskId}/users/{userId}")
    public void addUserToTask(@PathVariable(value = "taskId") Integer taskId, @PathVariable(value = "userId") Integer userId){
        if(taskId == null || userId == null) throw  new BadRequestException("Missing some require field");

        taskService.addUserToTask(userId, taskId);
    }
    @DeleteMapping("/{taskId}/users/{userId}")
    public String deleteUserOfTask(@PathVariable(value = "taskId") Integer taskId, @PathVariable(value = "userId") Integer userId){
        if(taskId == null || userId == null) throw  new BadRequestException("Missing some require field");

        taskService.deleteUserOfTask(taskId, userId);
        return ResponseMessage.DELETE_SUCCESS;
    }

    @GetMapping("/sum/{taskId}")
    public TimeSumResponse sumTimeByTask(@PathVariable(value = "taskId") Integer taskId){
        return null;
    }

    @GetMapping("/{taskId}/total-times")
    public Float getSumTimeByTaskId(@PathVariable("taskId") Integer taskId){
        if(taskId == null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "taskId");
        return timeService.sumTimeByTaskId(taskId);
    }

    @GetMapping("/{taskId}/users")
    public List<UserResponse> getAllUserByTaskId(@PathVariable("taskId") Integer taskId){
        if(taskId == null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "taskId");
        return userService.getAllByTaskId(taskId)
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{taskId}/users-did")
    public List<UserResponse> getAllUserDidByTaskId(@PathVariable("taskId") Integer taskId){
        if(taskId == null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "taskId");
        return userService.getAllDidByTaskId(taskId)
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{taskId}/users/{userId}/total-times")
    public Float getSumTimeByUserTask(@PathVariable("userId") Integer userId,@PathVariable("taskId") Integer taskId){
        if(taskId==null||userId==null) throw new BadRequestException(ExceptionMessage.MISSING_REQUIRE_FIELD.getMessage() + "taskId" +"or"+"userId");
        return timeService.sumTimeByUserTask(userId,taskId);
    }
}
