package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.TaskRequest;
import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TaskResponse;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import com.ces.intern.apitimecloud.http.response.TimeSumResponse;
import com.ces.intern.apitimecloud.service.TaskService;
import com.ces.intern.apitimecloud.service.TimeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/tasks")
//@ApiImplicitParams({@ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")}) use for each method
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;
    private final TimeService timeService;

    @Autowired
    public TaskController(TaskService taskService,
                          ModelMapper modelMapper,
                          TimeService timeService){
        this.taskService = taskService;
        this.modelMapper = modelMapper;
        this.timeService = timeService;
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
        if(timeRequest.getDescription() == null) throw new BadRequestException("Missing time description");

        System.out.println("===================================================================================");
        System.out.println(new Date(timeRequest.getMileSecondEndTime()) + "   " + new Date(timeRequest.getMileSecondStartTime()));
        System.out.println("===================================================================================");
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

    @GetMapping("/sum/{taskId}")
    public TimeSumResponse sumTimeByTask(@PathVariable(value = "taskId") Integer taskId){
        return null;
    }
}
