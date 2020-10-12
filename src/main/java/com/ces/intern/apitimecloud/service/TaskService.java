package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    TaskDTO createTask(Integer projectId, TaskDTO taskDTO, Integer userId);

    TaskDTO getTask(Integer taskId);

    List getAllTaskByProject(Integer projectId);

    TaskDTO updateTask(Integer projectId, TaskDTO taskDTO, Integer userId);

    void deleteTask(Integer taskId);

    void addUserToTask(Integer userId, Integer taskId);

    List getAllTaskByUser(Integer userId);

    List<TaskDTO> getAllByUserIdAndProjectId(Integer userId, Integer projectId);
}
