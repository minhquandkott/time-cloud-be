package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.request.TaskRequest;
import com.ces.intern.apitimecloud.http.request.TimeRequest;
import com.ces.intern.apitimecloud.http.response.TaskResponse;
import com.ces.intern.apitimecloud.http.response.TimeResponse;
import com.ces.intern.apitimecloud.service.TaskService;
import com.ces.intern.apitimecloud.service.TimeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;
    private ModelMapper modelMapper;
    private TimeService timeService;

    public TaskController(TaskService taskService,
                          ModelMapper modelMapper,
                          TimeService timeService){
        this.taskService = taskService;
        this.modelMapper = modelMapper;
        this.timeService = timeService;
    }



    @GetMapping("/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public TaskResponse getTask(@PathVariable Integer id){
        TaskDTO taskDTO = taskService.getTask(id);
        TaskResponse response = modelMapper.map(taskDTO, TaskResponse.class);
        return  response;
    }



    @PutMapping("/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public TaskResponse updateTask(@PathVariable Integer id, @RequestBody TaskRequest request){
        TaskDTO taskDTO = modelMapper.map(request,TaskDTO.class);
        taskDTO = taskService.updateTask(id,taskDTO);
        TaskResponse response = modelMapper.map(taskDTO,TaskResponse.class);
        return  response;
    }

    @PostMapping(value = "/{id}/times")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public TimeResponse createTime(@RequestHeader("userId") String userId,
                                   @RequestBody(required = true) TimeRequest timeRequest,
                                    @PathVariable("id") Integer taskId) {
        if(timeRequest.getDescription() == null) throw new BadRequestException("Missing time description");
        return timeService.save(userId, timeRequest, taskId);
    }

    @DeleteMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="JWT TOKEN", paramType="header")
    })
    public void deleteTask(@RequestBody Integer[] ids){
        taskService.deleteTask(ids);
    }

    @GetMapping("/{taskId}/users/{userId}/add")
    public void addUserToTask(@PathVariable(value = "taskId") Integer taskId, @PathVariable(value = "userId") Integer userId){
        if(taskId == null || userId == null) throw  new BadRequestException("Missing some require field");
        taskService.addUserToTask(userId, taskId);
    }
}
