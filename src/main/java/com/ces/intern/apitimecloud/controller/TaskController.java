package com.ces.intern.apitimecloud.controller;

import com.ces.intern.apitimecloud.dto.TaskDTO;
import com.ces.intern.apitimecloud.http.request.TaskRequest;
import com.ces.intern.apitimecloud.http.response.TaskResponse;
import com.ces.intern.apitimecloud.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;
    private ModelMapper modelMapper;

    public TaskController(TaskService taskService, ModelMapper modelMapper){
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/{id}")
    public TaskResponse createTask(@RequestBody TaskRequest request, @PathVariable Integer id,
                                   @RequestHeader("userId") String userId){

        TaskDTO task = modelMapper.map(request,TaskDTO.class);

        TaskDTO taskDTO = taskService.createTask(id,task,userId);

        TaskResponse response = modelMapper.map(taskDTO, TaskResponse.class);

        return response;
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable Integer id){
        TaskDTO taskDTO = taskService.getTask(id);
        TaskResponse response = modelMapper.map(taskDTO, TaskResponse.class);
        return  response;
    }

    @GetMapping("/{id}/project")
    public List<TaskResponse> getAllTaskByProjectId(@PathVariable Integer id){
        List<TaskDTO> list = taskService.getAllTaskByProject(id);
        return list.stream().map(task->modelMapper.map(task,TaskResponse.class)).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Integer id, @RequestBody TaskRequest request){
        TaskDTO taskDTO = modelMapper.map(request,TaskDTO.class);
        taskDTO = taskService.updateTask(id,taskDTO);
        TaskResponse response = modelMapper.map(taskDTO,TaskResponse.class);
        return  response;
    }

    @DeleteMapping("")
    public void deleteTask(@RequestBody Integer[] ids){
        taskService.deleteTask(ids);
    }
}
