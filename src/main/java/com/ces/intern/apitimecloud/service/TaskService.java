package com.ces.intern.apitimecloud.service;

import com.ces.intern.apitimecloud.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    public TaskDTO createTask(Integer projectId, TaskDTO taskDTO, String userId);

    public TaskDTO getTask(Integer taskId);

    public List getAllTaskByProject(Integer projectId);

    public TaskDTO updateTask(Integer projectId, TaskDTO taskDTO);

    public void deleteTask(Integer[] ids);

    public void addUserToTask(Integer userId, Integer taskId);
}
